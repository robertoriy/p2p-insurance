package ru.nsu.insurance.p2p.service.insurance;

import ru.nsu.insurance.p2p.dto.request.ContractDeployRequest;
import ru.nsu.insurance.p2p.dto.request.EthereumRequest;

public interface InsuranceService {
    void deploy(long userId, long groupId, ContractDeployRequest contractDeployRequest);

    void contribute(long userId, long groupId, EthereumRequest ethereumRequest);

    void refund(long userId, long groupId, EthereumRequest ethereumRequest);

    void reportInsuranceEvent(long userId, long groupId, EthereumRequest ethereumRequest);
}
