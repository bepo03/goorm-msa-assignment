package com.example.bookstore.order.model;

import com.example.bookstore.order.entity.Order;
import com.example.bookstore.order.entity.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderResponse(
        Long orderId,
        Long bookId,
        String bookTitle,
        int quantity,
        String customerName,
        BigDecimal totalPrice,
        OrderStatus status
) {
    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .bookId(order.getBookId())
                .bookTitle(order.getBookTitle())
                .quantity(order.getQuantity())
                .customerName(order.getCustomerName())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .build();
    }
}
