package com.example.bookstore.catalog.repository;

import com.example.bookstore.catalog.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
