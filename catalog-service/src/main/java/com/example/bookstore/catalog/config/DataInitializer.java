package com.example.bookstore.catalog.config;

import com.example.bookstore.catalog.entity.Book;
import com.example.bookstore.catalog.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final BookRepository bookRepository;

    @Bean
    public CommandLineRunner initBooks() {
        return args -> {
            if (bookRepository.count() > 0) {
                return;
            }

            bookRepository.saveAll(List.of(
                    Book.create("Spring Boot Start", "Alice", new BigDecimal("25000")),
                    Book.create("Gateway in Action", "Bob", new BigDecimal("32000")),
                    Book.create("MSA for Beginners", "Chris", new BigDecimal("28000"))
            ));
        };
    }
}
