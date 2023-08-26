package com.example.orderdelivery.order.workflow.registry;

import com.example.orderdelivery.order.core.OrderWriteService;
import com.example.orderdelivery.order.workflow.ConcreteOrderWorkflow;
import com.example.orderdelivery.order.workflow.OrderWorkflow;
import com.example.orderdelivery.order.workflow.event.request.OrderWorkflowRequestEventRepository;
import com.example.orderdelivery.order.workflow.event.response.OrderWorkflowResponseEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConcreteOrderWorkflowRegistry implements OrderWorkflowRegistry {

    private final OrderWriteService orderWriteService;

    private final OrderWorkflowRequestEventRepository requestEventRepository;
    private final OrderWorkflowResponseEventRepository responseEventRepository;

    private final Map<String, OrderWorkflow> workflows = new HashMap<>();

    @Override
    public OrderWorkflow newOrderWorkflow(String id) {
        if (!workflows.containsKey(id)) {
            workflows.put(id, new ConcreteOrderWorkflow(id, orderWriteService, requestEventRepository, responseEventRepository));
        }

        return workflows.get(id);
    }
}
