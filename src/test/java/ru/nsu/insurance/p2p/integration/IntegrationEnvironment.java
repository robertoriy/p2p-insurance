package ru.nsu.insurance.p2p.integration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.DirectoryResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@Testcontainers
public class IntegrationEnvironment {
    public static PostgreSQLContainer<?> POSTGRES;
    public static Path CHANGELOG_PATH =  Paths.get("liquibase/changelog/");
    public static String CHANGELOG_MAIN = "changelog-main.yml";

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("p2p_insurance")
            .withUsername("admin")
            .withPassword("1234");
        POSTGRES.start();

        try {
            runMigrations(POSTGRES);
        } catch (SQLException | LiquibaseException e) {
            log.error("Error running liquibase container");
            throw new RuntimeException(e);
        }
    }

    private static void runMigrations(JdbcDatabaseContainer<?> container) throws SQLException, LiquibaseException {
        try(Connection connection = DriverManager.getConnection(
            container.getJdbcUrl(),
            container.getUsername(),
            container.getPassword())
        ) {
            Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(
                CHANGELOG_MAIN,
                new DirectoryResourceAccessor(CHANGELOG_PATH),
                database
            );

            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | LiquibaseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}
