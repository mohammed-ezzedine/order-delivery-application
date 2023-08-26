package com.example.orderdelivery.order.workflow.event.request;

import com.example.orderdelivery.order.workflow.event.WorkflowEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class OrderWorkflowRequestEvent extends WorkflowEvent {

    public OrderWorkflowRequestEvent(String id, String workflowId) {
        super(id, workflowId);
    }
}