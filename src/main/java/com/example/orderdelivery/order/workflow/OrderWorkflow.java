package com.example.orderdelivery.order.workflow;

public interface OrderWorkflow {

    void start(StartOrderWorkflowRequest request);
    void addItemToOrder(String itemId, int quantity);
    void closeOrder();
}
