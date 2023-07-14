package de.workshops.bookshelf.controller;

import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.service.BookService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class BookRestControllerIntegrationTest {

    @LocalServerPort
    int port;

    RestTemplate rest;

    @MockBean
    BookService bookService;

    List<Book> allBooks = List.of(
            new Book("Book 1", "The first book", "Jack", "123456789"),
            new Book("Book 2", "The second book", "Jill", "987654321"),
            new Book("Book 3", "The third book", "Joe", "123454321")
    );

    @BeforeEach
    void setUpBookService() {
        when(bookService.getAllBooks()).thenReturn(allBooks);
    }

    @BeforeEach
    void setUpHttp() {
        RestAssured.port = port;

        rest = new RestTemplateBuilder().rootUri("http://localhost:" + port).build();
    }

    @Test
    void getAllBooks_should_return_json_array() {
        given().
            log().all().
        when().
            get("/book").
        then().
            log().all().
            statusCode(HttpStatus.OK.value()).
            body("author[0]", equalTo(allBooks.get(0).getAuthor()))
        ;
    }

    @Test
    void getAllBooks_with_RestTemplate() {
        Book[] books = rest.getForObject("/book", Book[].class);
        assertThat(books).hasSameSizeAs(allBooks);
    }
}
