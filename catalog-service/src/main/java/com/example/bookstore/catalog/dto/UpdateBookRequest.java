package com.example.bookstore.catalog.dto;

import java.math.BigDecimal;

public record UpdateBookRequest(
        String title,
        String author,
        BigDecimal price
) {
}
