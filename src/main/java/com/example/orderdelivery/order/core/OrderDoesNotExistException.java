package com.example.orderdelivery.order.core;

public class OrderDoesNotExistException extends Exception {
    public OrderDoesNotExistException(String id) {
        super(String.format("Order with ID '%s' does not exist.", id));
    }
}
