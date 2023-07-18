package de.workshops.bookshelf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
@Slf4j
public class PostgresContainerTestConfiguration {

    @Bean
    @ServiceConnection
    @RestartScope
    public PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>("postgres:15-alpine");
    }

    @Bean
    public ApplicationRunner postgresDetailsPrinter(PostgreSQLContainer<?> postgresContainer) {
        return args -> log.info(
                "\n\nURL: {}\nUser: {}\nPassword: {}\n\n",
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );
    }
}
