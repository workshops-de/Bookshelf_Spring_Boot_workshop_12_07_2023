package de.workshops.bookshelf.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.domain.BookNotFoundException;
import de.workshops.bookshelf.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerTest {

    @Autowired
    BookRestController bookRestController;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

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

    @Test
    void getAllBooks() {
        List<Book> books = bookRestController.getAllBooks();
        assertThat(books).hasSameSizeAs(allBooks);
    }

    @Test
    void getAllBooks_should_return_json_array() throws Exception {
        mvc.perform(get("/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(allBooks.size())))
                .andExpect(jsonPath("$[1].title", is(allBooks.get(1).getTitle())))
        ;
    }

    @Test
    void getAllBooks_should_return_json_array_containing_book_objects() throws Exception {
        MvcResult result = mvc.perform(get("/book")).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        String responseBody = result.getResponse().getContentAsString();
        List<Book> books = objectMapper.readValue(responseBody, new TypeReference<>() {
        });
        assertThat(books).hasSameSizeAs(allBooks);
        assertThat(books.get(1).getTitle()).isEqualTo(allBooks.get(1).getTitle());
    }

    @Test
    void getSingleBookByIsbn_with_unknown_isbn_should_return_404() throws Exception {
        String isbn = "123123123";
        String exceptionMessage = "Unknown ISBN: 123123123";
        when(bookService.getSingleBookByIsbn(isbn)).thenThrow(new BookNotFoundException(exceptionMessage));

        mvc.perform(get("/book/{isbn}", isbn))
                .andExpect(status().isNotFound())
                .andExpect(content().string(exceptionMessage))
        ;
    }

    @Test
    void createBook_should_create_book() throws Exception {
        Book book = new Book("Spring Boot 3", "Everything about the new version", "Someone", "987656789");
        String jsonBody = objectMapper.writeValueAsString(book);

        mvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
        ;
    }

    @TestConfiguration
    static class BookRestControllerTestConfiguration {

        @Bean
        ObjectMapper objectMapper() {
            return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        }
    }
}