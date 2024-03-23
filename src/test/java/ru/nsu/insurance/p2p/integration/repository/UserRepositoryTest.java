package ru.nsu.insurance.p2p.integration.repository;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nsu.insurance.p2p.dao.UserRepository;
import ru.nsu.insurance.p2p.dao.entity.UserProfile;
import ru.nsu.insurance.p2p.integration.IntegrationEnvironment;

@Slf4j
@SpringBootTest
public class UserRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private UserRepository userRepository;

    @Test
    void test() {
        UserProfile user = UserProfile.builder()
            .username("sasha12")
            .password("1112221")
            .firstName("Alex")
            .surname("Zub")
            .dateOfBirth(LocalDate.of(1990, 1, 1))
            .ethereumAddress("user-address")
            .build();
        UserProfile savedUser = userRepository.save(user);
        log.info(savedUser.toString());
        Assertions.assertThat(savedUser.getId()).isEqualTo(4L);

        UserProfile fromRepo = userRepository.findById(4L).get();
        Assertions.assertThat(fromRepo.getUsername()).isEqualTo("sasha12");
    }
}
