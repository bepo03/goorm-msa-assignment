package com.example.bookstore.catalog.service;

import com.example.bookstore.catalog.dto.BookResponse;
import com.example.bookstore.catalog.entity.Book;
import com.example.bookstore.catalog.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final BookRepository bookRepository;

    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream()
                .map(BookResponse::from)
                .toList();
    }

    public BookResponse findById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다. id=" + bookId));

        return BookResponse.from(book);
    }
}
