package com.example.orderdelivery.order.workflow;

import com.example.orderdelivery.order.core.Order;
import com.example.orderdelivery.order.core.OrderFetcher;
import com.example.orderdelivery.order.workflow.registry.OrderWorkflowRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderWorkflowsStarter implements CommandLineRunner {

    private final OrderFetcher orderFetcher;
    private final OrderWorkflowRegistry workflowRegistry;

    @Override
    public void run(String... args) {
        List<Order> openOrder = orderFetcher.getOpenOrder();
        log.info("will be resuming orders {}", openOrder);
        for (Order order : openOrder) {
            OrderWorkflow orderWorkflow = workflowRegistry.newOrderWorkflow(order.getId());
            new Thread(() -> orderWorkflow.start(StartOrderWorkflowRequest.builder().build())).start();
        }
    }
}
