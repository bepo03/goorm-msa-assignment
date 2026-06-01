package com.example.bookstore.order.exception;

import com.example.bookstore.order.common.exception.BusinessException;
import com.example.bookstore.order.common.exception.ErrorCode;

public class OrderNotFoundException extends BusinessException {

    public OrderNotFoundException(Long orderId) {
        super(ErrorCode.ORDER_NOT_FOUND, "주문을 찾을 수 없습니다. (id: " + orderId + ")");
    }
}
