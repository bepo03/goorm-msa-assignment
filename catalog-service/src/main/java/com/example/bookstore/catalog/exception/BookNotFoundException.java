package com.example.bookstore.catalog.exception;

import com.example.bookstore.catalog.common.exception.BusinessException;
import com.example.bookstore.catalog.common.exception.ErrorCode;

public class BookNotFoundException extends BusinessException {

    public BookNotFoundException(Long bookId) {
        super(ErrorCode.BOOK_NOT_FOUND, "책을 찾을 수 없습니다. (id: " + bookId + ")");
    }
}
