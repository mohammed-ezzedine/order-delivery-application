package com.example.orderdelivery.order.api;

import com.example.orderdelivery.order.core.OrderDoesNotExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class OrderNotFoundAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OrderDoesNotExistException.class)
    public ResponseEntity<Object> handleOrderNotFound(OrderDoesNotExistException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
