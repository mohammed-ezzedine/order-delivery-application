package com.example.orderdelivery.order.workflow.event.request;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderWorkflowRequestEventRepository extends MongoRepository<OrderWorkflowRequestEvent, String> {
    List<OrderWorkflowRequestEvent> findAllByWorkflowId(String workflowId);
}
