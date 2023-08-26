package com.example.orderdelivery.order.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddItemToOrderRequest {
    private String orderId;
    private String itemId;
    private int quantity;
}
