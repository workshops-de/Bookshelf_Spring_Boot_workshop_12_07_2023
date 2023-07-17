package de.workshops.bookshelf.configuration;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationPropertiesTest {

    @Nested
    @ActiveProfiles("prod")
    class InProduction {

        @Value("${server.port}")
        private int port;

        @Test
        void port_should_be_8090() {
            assertThat(port).isEqualTo(8090);
        }
    }

    @Nested
    class DuringDevelopment {

        @Value("${server.port:8080}")
        private int port;

        @Test
        void port_should_be_8080() {
            assertThat(port).isEqualTo(8080);
        }
    }
}
