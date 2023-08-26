package com.example.orderdelivery.order.workflow.event.response;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderWorkflowResponseEventRepository extends MongoRepository<OrderWorkflowResponseEvent, String> {
    Optional<OrderWorkflowResponseEvent> findByWorkflowIdAndCorrelationId(String workflowId, String correlationId);
}
