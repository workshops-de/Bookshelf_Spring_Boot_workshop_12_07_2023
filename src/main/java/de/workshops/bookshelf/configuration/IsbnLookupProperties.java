package de.workshops.bookshelf.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IsbnLookupProperties {

    private String url;

    private String apiKey;
}
