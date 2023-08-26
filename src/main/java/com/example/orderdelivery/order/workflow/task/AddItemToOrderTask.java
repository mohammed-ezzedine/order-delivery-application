package com.example.orderdelivery.order.workflow.task;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AddItemToOrderTask extends OrderWorkflowTask {
    public static final String TASK_TYPE = "AddItemToOrderTask";

    private String itemId;
    private int itemQuantity;

    @Override
    public String getTaskType() {
        return TASK_TYPE;
    }
}
