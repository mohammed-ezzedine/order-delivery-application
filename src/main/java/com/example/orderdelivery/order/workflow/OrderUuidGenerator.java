package com.example.orderdelivery.order.workflow;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderUuidGenerator implements OrderIdGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
