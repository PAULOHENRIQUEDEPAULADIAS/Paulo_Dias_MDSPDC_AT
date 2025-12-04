package com.example.db_service.repository;

import com.example.db_service.model.CallLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import reactor.test.StepVerifier;

@Testcontainers
@DataR2dbcTest
class DbServiceRepositoryIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres")
            .withInitScript("schema.sql");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url",
                () -> "r2dbc:postgresql://" +
                        postgres.getHost() + ":" +
                        postgres.getMappedPort(5432) + "/" +
                        postgres.getDatabaseName());

        registry.add("spring.r2dbc.username", postgres::getUsername);
        registry.add("spring.r2dbc.password", postgres::getPassword);
    }

    @Autowired
    DbServiceRepository repository;

    @Test
    void saveAndFindAll_ShouldPersistLog() {
        CallLog log = new CallLog("test-service", "hello");

        StepVerifier.create(repository.save(log))
                .expectNextMatches(saved -> saved.getId() != null)
                .verifyComplete();
    }
}
