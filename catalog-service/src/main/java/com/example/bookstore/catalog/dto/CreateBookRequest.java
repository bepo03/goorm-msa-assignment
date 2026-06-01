package com.example.bookstore.catalog.dto;

import java.math.BigDecimal;

public record CreateBookRequest(
        String title,
        String author,
        BigDecimal price
) {
}
