package ru.iashinme.blog.repository;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractIntegrationDbTest {

    protected final static PostgreSQLContainer<?> postgresqlContainer = getContainer();

    private static PostgreSQLContainer<?> getContainer() {

        PostgreSQLContainer<?> container;

        try (PostgreSQLContainer<?> c = new PostgreSQLContainer<>("postgres:13.3")) {
            container = c;
        }

        return container.withUsername("postgresql")
                .withPassword("letmein")
                .withDatabaseName("test_db")
                .withReuse(true);
    }


    @DynamicPropertySource
    public static void startPostgresContainer(DynamicPropertyRegistry registry) {
        postgresqlContainer.start();

        assertThat(postgresqlContainer.isRunning()).isTrue();

        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }
}
