package com.example.orderdelivery.order.workflow;

import com.example.orderdelivery.order.core.AddItemToOrderRequest;
import com.example.orderdelivery.order.core.OrderDoesNotExistException;
import com.example.orderdelivery.order.core.OrderFetcher;
import com.example.orderdelivery.order.core.OrderNotifier;
import com.example.orderdelivery.order.workflow.registry.OrderWorkflowRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderWorkflowNotifier implements OrderNotifier {

    private final OrderWorkflowRegistry workflowRegistry;
    private final OrderFetcher orderFetcher;

    @Override
    public void addItemToOrder(AddItemToOrderRequest request) throws OrderDoesNotExistException {
        validateOrderExists(request.getOrderId());

        OrderWorkflow workflow = workflowRegistry.newOrderWorkflow(request.getOrderId());
        new Thread(() -> workflow.start(StartOrderWorkflowRequest.builder().build())).start();
        workflow.addItemToOrder(request.getItemId(), request.getQuantity());
    }

    @Override
    public void closeOrder(String id) throws OrderDoesNotExistException {
        validateOrderExists(id);

        OrderWorkflow workflow = workflowRegistry.newOrderWorkflow(id);
        new Thread(() -> workflow.start(StartOrderWorkflowRequest.builder().build())).start();
        workflow.closeOrder();
    }

    private void validateOrderExists(String orderId) throws OrderDoesNotExistException {
        if (!orderFetcher.orderExists(orderId)) {
            throw new OrderDoesNotExistException(orderId);
        }
    }
}
