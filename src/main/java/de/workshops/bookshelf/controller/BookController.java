package de.workshops.bookshelf.controller;

import de.workshops.bookshelf.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("listOfAllBooks", service.getAllBooks());

        return "books_overview";
    }
}
