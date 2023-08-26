package com.example.orderdelivery.order.api;

import com.example.orderdelivery.item.core.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderApiModel {
    private String id;
    private String status;
    private List<Item> items;
}
