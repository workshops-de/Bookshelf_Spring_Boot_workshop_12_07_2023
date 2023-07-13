package de.workshops.bookshelf.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookSearchRequest {

    private String isbn;

    private String author;
}
