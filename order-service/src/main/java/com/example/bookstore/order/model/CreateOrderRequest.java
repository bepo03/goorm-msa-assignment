package com.example.bookstore.order.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
        @NotNull Long bookId,
        @Min(1) int quantity,
        @NotBlank String customerName
) {
}
