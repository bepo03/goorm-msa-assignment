package com.example.bookstore.catalog.service;

import com.example.bookstore.catalog.dto.BookResponse;
import com.example.bookstore.catalog.dto.CreateBookRequest;
import com.example.bookstore.catalog.dto.UpdateBookRequest;
import com.example.bookstore.catalog.entity.Book;
import com.example.bookstore.catalog.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream()
                .map(BookResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookResponse findById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다. id=" + bookId));

        return BookResponse.from(book);
    }

    @Transactional
    public BookResponse create(CreateBookRequest request) {
        Book book = bookRepository.save(Book.create(
                request.title(),
                request.author(),
                request.price()
        ));

        return BookResponse.from(book);
    }

    @Transactional
    public BookResponse update(Long bookId, UpdateBookRequest request) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다. id=" + bookId));

        book.update(request.title(), request.author(), request.price());

        return BookResponse.from(book);
    }
}
