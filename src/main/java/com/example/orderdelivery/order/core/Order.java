package com.example.orderdelivery.order.core;

import com.example.orderdelivery.item.core.Item;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Order {
    private String id;
    private OrderStatus status;
    @Builder.Default
    private List<Item> items = new ArrayList<>();
}
