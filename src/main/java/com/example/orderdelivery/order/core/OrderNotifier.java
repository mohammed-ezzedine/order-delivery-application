package com.example.orderdelivery.order.core;

public interface OrderNotifier {
    void addItemToOrder(AddItemToOrderRequest request) throws OrderDoesNotExistException;

    void closeOrder(String id) throws OrderDoesNotExistException;
}
