package com.example.bookstore.catalog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;

    public static Book create(String title, String author, BigDecimal price) {
        Book book = new Book();
        book.title = title;
        book.author = author;
        book.price = price;
        return book;
    }

    public void update(String title, String author, BigDecimal price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
}
