package ru.nsu.insurance.p2p.service.insurance;

import java.math.BigInteger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import ru.nsu.insurance.p2p.dao.ContractRepository;
import ru.nsu.insurance.p2p.dao.GroupRepository;
import ru.nsu.insurance.p2p.dao.UserRepository;
import ru.nsu.insurance.p2p.dao.entity.InsuranceContract;
import ru.nsu.insurance.p2p.dao.entity.InsuranceGroup;
import ru.nsu.insurance.p2p.dao.entity.UserProfile;
import ru.nsu.insurance.p2p.dto.request.ContractDeployRequest;
import ru.nsu.insurance.p2p.dto.request.EthereumRequest;
import ru.nsu.insurance.p2p.service.exception.InsuranceAccessDeniedException;
import ru.nsu.insurance.p2p.service.exception.InsuranceServiceException;
import ru.nsu.insurance.p2p.service.exception.existence.ContractNotFoundException;
import ru.nsu.insurance.p2p.service.exception.existence.GroupNotFoundException;
import ru.nsu.insurance.p2p.service.exception.existence.UserNotFoundException;
import ru.nsu.insurance.p2p.service.insurance.contract.Insurance;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultInsuranceService implements InsuranceService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ContractRepository contractRepository;
    private final Web3j web3j;

    @Override
    public void deploy(long userId, long groupId, ContractDeployRequest deployRequest) {
        log.info("User {} in group {} is trying to deploy contract", userId, groupId);

        checkExistence(userId, groupId);
        checkPermissions(userId, groupId);

        if (contractExists(groupId)) {
            pureRefund(groupId, deployRequest.credentials().privateKey());
        }
        Insurance contract = createContract(groupId, deployRequest);
        saveContract(groupId, contract.getContractAddress(), deployRequest.contributeEthValue());
    }

    @Override
    public void contribute(long userId, long groupId, EthereumRequest ethereumRequest) {
        log.info("User {} in group {} is trying to contribute to contract", userId, groupId);

        checkExistence(userId, groupId);
        checkPermissions(userId, groupId);

        if (!contractExists(groupId)) {
            throw new ContractNotFoundException();
        }

        Insurance contract = getContract(groupId, ethereumRequest.privateKey());
        BigInteger valueToContribute = getValueToContribute(groupId);

        try {
            contract.contribute(valueToContribute).send();
        } catch (Exception e) {
            log.error("AAAAAAAAAAAAAa" + e.getMessage());
        }
    }

    @Override
    public void refund(long userId, long groupId, EthereumRequest ethereumRequest) {
        log.info("User {} in group {} is trying to refund", userId, groupId);

        checkExistence(userId, groupId);
        checkPermissions(userId, groupId);

        pureRefund(groupId, ethereumRequest.privateKey());
    }

    @Override
    public void reportInsuranceEvent(long userId, long groupId, EthereumRequest ethereumRequest) {
        log.info("User {} in group {} is trying to report insurance event", userId, groupId);

        checkExistence(userId, groupId);
        checkPermissions(userId, groupId);

        Insurance contract = getContract(groupId, ethereumRequest.privateKey());

        try {
            contract.reportInsuranceEvent().send();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void checkExistence(long userId, long groupId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        if (!groupRepository.existsById(groupId)) {
            throw new GroupNotFoundException();
        }
    }

    private void checkPermissions(long userId, long groupId) {
        UserProfile user = userRepository
            .findById(userId)
            .orElseThrow(UserNotFoundException::new);

        List<Long> groupIds = user
            .getGroups()
            .stream()
            .map(InsuranceGroup::getId)
            .toList();

        if (!groupIds.contains(groupId)) {
            throw new InsuranceAccessDeniedException();
        }
    }

    private boolean contractExists(long groupId) {
        InsuranceGroup group = groupRepository
            .findById(groupId)
            .orElseThrow(GroupNotFoundException::new);

        return group.getContract() != null;
    }

    private BigInteger getValueToContribute(long groupId) {
        long contributeValue = getContractValue(groupId);
        return EthereumUtils.convertToETH(contributeValue);
    }

    private Insurance getContract(long groupId, String privateKey) {
        log.info("getting contract by pk: {}", privateKey);
        Credentials credentials = Credentials.create(privateKey);
        String contractAddress = getContractAddress(groupId);
        log.info("contract address: {}", contractAddress);
        return loadContract(contractAddress, credentials);
    }

    private List<String> getAccountAddresses(long groupId) {
        InsuranceGroup group = groupRepository
            .findById(groupId)
            .orElseThrow(GroupNotFoundException::new);

        return group.getUsers()
            .stream()
            .map(UserProfile::getEthereumAddress)
            .toList();
    }

    private String getContractAddress(long groupId) {
        InsuranceGroup group = groupRepository
            .findById(groupId)
            .orElseThrow(GroupNotFoundException::new);

        return group.getContract().getAddress();
    }

    private long getContractValue(long groupId) {
        InsuranceGroup group = groupRepository
            .findById(groupId)
            .orElseThrow(GroupNotFoundException::new);

        return group.getContract().getContributeValue();
    }

    private Insurance loadContract(String contractAddress, Credentials credentials) {
        try {
            return Insurance.load(
                contractAddress,
                web3j,
                credentials,
                EthereumUtils.GAS_PRICE,
                EthereumUtils.GAS_LIMIT
            );
        } catch (Exception e) {
            log.error("loading error: {}", e.getMessage());
            throw new InsuranceServiceException("Failed to load contract", e);
        }
//        try {
//            return Insurance.load(
//                contractAddress,
//                web3j,
//                credentials,
//                new DefaultGasProvider()
//            );
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new InsuranceServiceException("Failed to load contract", e);
//        }
    }

    private void saveContract(long groupId, String contractAddress, long contributeValue) {
        InsuranceGroup group = groupRepository
            .findById(groupId)
            .orElseThrow(GroupNotFoundException::new);

        InsuranceContract insuranceContract = InsuranceContract.builder()
            .address(contractAddress)
            .contributeValue(contributeValue)
            .group(group)
            .build();
        group.setContract(insuranceContract);
        contractRepository.save(insuranceContract);
    }

    private Insurance createContract(long groupId, ContractDeployRequest deployRequest) {
        List<String> addresses = getAccountAddresses(groupId);
        Credentials credentials = Credentials.create(deployRequest.credentials().privateKey());
        BigInteger ethereumValue = EthereumUtils.convertToETH(deployRequest.contributeEthValue());

        try {
            return Insurance.deploy(
                web3j,
                credentials,
                EthereumUtils.GAS_PRICE,
                EthereumUtils.GAS_LIMIT,
                ethereumValue,
                addresses
            ).send();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InsuranceServiceException("Failed to deploy contract", e);
        }
//        try {
//            return Insurance.deploy(
//                web3j,
//                credentials,
//                new DefaultGasProvider(),
//                ethereumValue,
//                addresses
//            ).send();
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new InsuranceServiceException("Failed to deploy contract", e);
//        }
    }

    private void pureRefund(long groupId, String privateKey) {
        Insurance contract = getContract(groupId, privateKey);
        try {
            contract.refund().send();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
