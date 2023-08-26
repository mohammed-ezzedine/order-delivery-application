package com.example.orderdelivery.order.core;

public interface OrderReadService {
    Order get(String id) throws OrderDoesNotExistException;
}
