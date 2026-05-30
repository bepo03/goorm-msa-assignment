package com.example.bookstore.catalog.dto;

import com.example.bookstore.catalog.entity.Book;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BookResponse(
        Long id,
        String title,
        String author,
        BigDecimal price
) {
    public static BookResponse from(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .build();
    }
}
