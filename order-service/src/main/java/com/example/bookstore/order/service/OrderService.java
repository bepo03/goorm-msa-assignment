package com.example.bookstore.order.service;

import com.example.bookstore.order.client.CatalogClient;
import com.example.bookstore.order.model.BookSummary;
import com.example.bookstore.order.model.CreateOrderRequest;
import com.example.bookstore.order.model.OrderResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final CatalogClient catalogClient;
    private final AtomicLong orderSequence = new AtomicLong(1);
    private final List<OrderResponse> orders = new ArrayList<>();

    public OrderService(CatalogClient catalogClient) {
        this.catalogClient = catalogClient;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        BookSummary book = catalogClient.getBook(request.bookId());
        BigDecimal totalPrice = book.price().multiply(BigDecimal.valueOf(request.quantity()));

        OrderResponse order = new OrderResponse(
                orderSequence.getAndIncrement(),
                book.id(),
                book.title(),
                request.quantity(),
                request.customerName(),
                totalPrice
        );

        orders.add(order);
        return order;
    }

    public List<OrderResponse> findAll() {
        return List.copyOf(orders);
    }
}
