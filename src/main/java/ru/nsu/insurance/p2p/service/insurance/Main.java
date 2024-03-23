package ru.nsu.insurance.p2p.service.insurance;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import ru.nsu.insurance.p2p.service.insurance.contract.Insurance;
import ru.nsu.insurance.p2p.service.insurance.model.Account;

import java.math.BigInteger;
import java.util.List;

public class Main {
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    private static final String CONTRACT = "0x60182D205d096910a97d4c5B0B6741D8C2C5fc55";

    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
        Web3j web3 = Web3j.build(new HttpService("http://localhost:7545"));

        List<Account> accounts = List.of(
                new Account(
                        "0x61920393c0656571fc224b10300b0b02Dab2D5aA",
                        "0xd8f15ecc9dbe5437ace1b0972f62170f79e52437dc2f0877108140e29141f606"
                ),
                new Account(
                        "0xa6ab395EFDc8ca6152F93d3EC72EC6428285fab5",
                        "0xe4f756be0081e4a64f40b3a9900fdaf96976a48fdd709b48b63529dac45392a5"
                )
        );

        Credentials credentialsFirst = Credentials.create(accounts.get(0).privateKey());
        Credentials credentialsSecond = Credentials.create(accounts.get(1).privateKey());

        DefaultGasProvider contractGasProvider = new DefaultGasProvider();

        BigInteger contributeValue = BigInteger.valueOf(5 * 1000000000000000000L);
        List<String> addresses = List.of(accounts.get(0).address(), accounts.get(1).address());

//        Insurance contractForFirst = Insurance.deploy(
//                web3, credentialsFirst, GAS_PRICE, GAS_LIMIT, contributeValue, addresses
//        ).send();

        Insurance contractFirst = Insurance.load(
            CONTRACT, web3, credentialsFirst, GAS_PRICE, GAS_LIMIT
        );
        Insurance contractSecond = Insurance.load(
            CONTRACT, web3, credentialsSecond, GAS_PRICE, GAS_LIMIT
        );

        contractFirst.contribute(contributeValue).send();
        contractSecond.contribute(contributeValue).send();
        contractFirst.reportInsuranceEvent().send();
//
//        Insurance contractForSecond = Insurance.load(
//                contractForFirst.getContractAddress(), web3, credentialsSecond, contractGasProvider
//        );
//
//

//        Transfer.sendFunds(
//                web3,
//                credentialsFirst,
//                accounts.get(1).address(),
//                BigDecimal.valueOf(15.0),
//                Convert.Unit.ETHER
//        ).send();
    }
}
