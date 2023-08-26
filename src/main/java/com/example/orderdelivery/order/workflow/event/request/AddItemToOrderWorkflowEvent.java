package com.example.orderdelivery.order.workflow.event.request;


import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddItemToOrderWorkflowEvent extends OrderWorkflowRequestEvent {
    public static final String TYPE = "AddItemToOrderWorkflowEvent";

    private String itemId;
    private int itemQuantity;

    @Builder
    public AddItemToOrderWorkflowEvent(String itemId, int itemQuantity, String id, String workflowId) {
        super(id, workflowId);
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
    }

    @Override
    public String getEventType() {
        return TYPE;
    }
}
