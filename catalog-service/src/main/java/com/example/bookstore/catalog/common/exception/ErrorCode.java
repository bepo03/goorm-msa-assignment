package com.example.bookstore.catalog.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOK_NOT_FOUND", "책을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
