package com.example.orderdelivery.order.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddItemToOrderApiRequest {
    private String itemId;
    private int itemQuantity;
}
