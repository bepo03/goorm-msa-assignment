package com.example.bookstore.catalog.model;

import java.math.BigDecimal;

public record BookResponse(
        Long id,
        String title,
        String author,
        BigDecimal price
) {
}
