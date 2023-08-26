package com.example.orderdelivery.order.store;

import com.example.orderdelivery.item.core.Item;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document
public class OrderEntity {
    private String id;
    private String status;
    private List<Item> items;
}
