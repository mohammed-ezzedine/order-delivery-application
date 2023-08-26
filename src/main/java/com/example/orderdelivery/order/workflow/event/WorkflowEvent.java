package com.example.orderdelivery.order.workflow.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class WorkflowEvent {
    private String id;
    private String workflowId;

    public abstract String getEventType();
}
