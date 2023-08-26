package com.example.orderdelivery.order.workflow.registry;

import com.example.orderdelivery.order.workflow.OrderWorkflow;

public interface OrderWorkflowRegistry {
    OrderWorkflow newOrderWorkflow(String id);
}
