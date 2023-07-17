package de.workshops.bookshelf.controller;

import de.workshops.bookshelf.configuration.BookshelfProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoRestController {

    private final BookshelfProperties properties;

    public InfoRestController(BookshelfProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/name")
    public String getBookshelfName() {
        return properties.getName();
    }

    @GetMapping("/isbn-lookup/url")
    public String getIsbnLookupUrl() {
        return properties.getIsbnLookup().getUrl();
    }
}
