package com.example.orderdelivery.item.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private String id;
    private int quantity;
}
