package com.example.bookstore.order.common.response;

import com.example.bookstore.order.common.exception.ErrorCode;

public record ErrorResponse(
        boolean success,
        String errorCode,
        String message
) {

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(false, errorCode.getCode(), errorCode.getMessage());
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(false, errorCode.getCode(), message);
    }
}
