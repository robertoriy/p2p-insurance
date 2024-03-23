package ru.nsu.insurance.p2p.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class InsuranceConfig {
    @Bean
    public Web3j getWeb3j(HttpService httpService) {
        return Web3j.build(httpService);
    }

    @Bean
    public HttpService getHttpService(ApplicationConfig applicationConfig) {
        return new HttpService(applicationConfig.ethereum());
    }
}
