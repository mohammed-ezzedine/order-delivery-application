package com.example.orderdelivery.order.workflow;

import com.example.orderdelivery.order.core.OrderCreator;
import com.example.orderdelivery.order.core.OrderDoesNotExistException;
import com.example.orderdelivery.order.core.OrderWriteService;
import com.example.orderdelivery.order.workflow.registry.OrderWorkflowRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderWorkflowStarter implements OrderCreator {

    private final OrderWorkflowRegistry workflowRegistry;
    private final OrderIdGenerator idGenerator;
    private final OrderWriteService orderWriteService;

    @Override
    public String createOrder() {
        String orderId = idGenerator.generate();
        OrderWorkflow orderWorkflow = workflowRegistry.newOrderWorkflow(orderId);
        persistOrder(orderId);
        new Thread(() -> orderWorkflow.start(StartOrderWorkflowRequest.builder().build())).start();
        updateOrderStatusToOpened(orderId);
        return orderId;
    }

    private void persistOrder(String orderId) {
        orderWriteService.orderCreated(orderId);
    }

    private void updateOrderStatusToOpened(String orderId) {
        try {
            orderWriteService.orderOpened(orderId);
        } catch (OrderDoesNotExistException e) {
            throw new IllegalStateException();
        }
    }
}
