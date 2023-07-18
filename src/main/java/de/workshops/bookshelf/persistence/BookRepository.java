package de.workshops.bookshelf.persistence;

import de.workshops.bookshelf.domain.Book;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends ListCrudRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByAuthor(String author);

    List<Book> findByIsbnAndAuthor(String isbn, String author);
}
