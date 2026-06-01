package com.example.bookstore.order.service;

import com.example.bookstore.order.client.CatalogClient;
import com.example.bookstore.order.entity.Order;
import com.example.bookstore.order.model.BookSummary;
import com.example.bookstore.order.model.CreateOrderRequest;
import com.example.bookstore.order.model.OrderResponse;
import com.example.bookstore.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CatalogClient catalogClient;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(CreateOrderRequest request) {
        BookSummary book = catalogClient.getBook(request.bookId());
        BigDecimal totalPrice = book.price().multiply(BigDecimal.valueOf(request.quantity()));

        Order order = orderRepository.save(Order.create(
                book.id(),
                book.title(),
                request.quantity(),
                request.customerName(),
                totalPrice
        ));

        return OrderResponse.from(order);
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::from)
                .toList();
    }
}
