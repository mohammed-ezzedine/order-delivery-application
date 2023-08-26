package com.example.orderdelivery.order.workflow.event.response;

import com.example.orderdelivery.order.workflow.event.WorkflowEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class OrderWorkflowResponseEvent extends WorkflowEvent {
    private String correlationId;

    public OrderWorkflowResponseEvent(String id, String workflowId, String correlationId) {
        super(id, workflowId);
        this.correlationId = correlationId;
    }
}
