package com.example.orderdelivery.order.core;

public interface OrderWriteService {
    void orderCreated(String orderId);
    void orderOpened(String orderId) throws OrderDoesNotExistException;
    void orderClosed(String orderId) throws OrderDoesNotExistException;

    void itemAddedToOrder(String orderId, String itemId, int quantity) throws OrderDoesNotExistException;
}
