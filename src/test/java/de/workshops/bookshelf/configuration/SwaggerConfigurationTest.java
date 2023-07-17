package de.workshops.bookshelf.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class SwaggerConfigurationTest {

    @Nested
    @ActiveProfiles("prod")
    class InProduction {

        @LocalServerPort
        int port;

        RestTemplate rest;

        @BeforeEach
        void setUp() {
            rest = new RestTemplateBuilder().rootUri("http://localhost:" + port).build();
        }

        @Test
        void swagger_should_not_be_available() {
            try {
                ResponseEntity<String> response = rest.getForEntity("/swagger-ui/index.html", String.class);
                assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
            } catch (NotFound ignored) {
            }
        }
    }

    @Nested
    class DuringDevelopment {

        @LocalServerPort
        int port;

        RestTemplate rest;

        @BeforeEach
        void setUp() {
            rest = new RestTemplateBuilder().rootUri("http://localhost:" + port).build();
        }

        @Test
        void swagger_should_be_available() {
            ResponseEntity<String> response = rest.getForEntity("/swagger-ui/index.html", String.class);
            assertThat(response.getStatusCode()).isEqualTo(OK);
        }
    }
}
