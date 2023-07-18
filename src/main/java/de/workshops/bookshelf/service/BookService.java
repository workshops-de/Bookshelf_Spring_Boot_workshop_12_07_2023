package de.workshops.bookshelf.service;

import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.domain.BookNotFoundException;
import de.workshops.bookshelf.persistence.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book getSingleBookByIsbn(String isbn) {
        return repository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Unknown ISBN: " + isbn));
    }

    public Book searchBookByAuthor(String author) {
        return repository.findByAuthor(author)
                .orElseThrow();
    }

    public List<Book> searchBooksByIsbnAndAuthor(String isbn, String author) {
        return repository.findByIsbnAndAuthor(isbn, author);
    }

    public void saveBook(Book book) {
        repository.save(book);
    }
}
