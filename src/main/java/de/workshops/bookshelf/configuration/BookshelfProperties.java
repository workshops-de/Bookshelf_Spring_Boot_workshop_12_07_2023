package de.workshops.bookshelf.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "bookshelf")
@Getter
@Setter
public class BookshelfProperties {

    /**
     * The name of my bookshelf.
     */
    private String name;

    @NestedConfigurationProperty
    private IsbnLookupProperties isbnLookup = new IsbnLookupProperties();
}
