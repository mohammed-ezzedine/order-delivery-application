package com.example.orderdelivery.order.workflow.task;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class OrderWorkflowTask {
    private String correlationId;

    public abstract String getTaskType();
}
