package com.example.bookstore.catalog.service;

import com.example.bookstore.catalog.model.BookResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CatalogService {

    private final List<BookResponse> books = List.of(
            new BookResponse(1L, "Spring Boot Start", "Alice", new BigDecimal("25000")),
            new BookResponse(2L, "Gateway in Action", "Bob", new BigDecimal("32000")),
            new BookResponse(3L, "MSA for Beginners", "Chris", new BigDecimal("28000"))
    );

    public List<BookResponse> findAll() {
        return books;
    }

    public BookResponse findById(Long bookId) {
        return books.stream()
                .filter(book -> book.id().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Book not found. id=" + bookId));
    }
}
