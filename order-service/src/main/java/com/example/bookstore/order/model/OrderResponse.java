package com.example.bookstore.order.model;

import java.math.BigDecimal;

public record OrderResponse(
        Long orderId,
        Long bookId,
        String bookTitle,
        int quantity,
        String customerName,
        BigDecimal totalPrice
) {
}
