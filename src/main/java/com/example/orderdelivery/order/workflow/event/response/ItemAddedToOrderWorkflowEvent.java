package com.example.orderdelivery.order.workflow.event.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ItemAddedToOrderWorkflowEvent extends OrderWorkflowResponseEvent {

    public static final String TYPE = "ItemAddedToOrderWorkflowEvent";

    @Builder
    public ItemAddedToOrderWorkflowEvent(String id, String workflowId, String correlationId) {
        super(id, workflowId, correlationId);
    }

    @Override
    public String getEventType() {
        return TYPE;
    }
}
