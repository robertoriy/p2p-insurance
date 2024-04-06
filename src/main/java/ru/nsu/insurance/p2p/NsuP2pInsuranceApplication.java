package ru.nsu.insurance.p2p;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.nsu.insurance.p2p.configuration.ApplicationConfig;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
@EnableJpaRepositories(basePackages = "ru.nsu.insurance.p2p")
public class NsuP2pInsuranceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NsuP2pInsuranceApplication.class, args);
    }

}
