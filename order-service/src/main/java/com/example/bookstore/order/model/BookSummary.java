package com.example.bookstore.order.model;

import java.math.BigDecimal;

public record BookSummary(
        Long id,
        String title,
        String author,
        BigDecimal price
) {
}
