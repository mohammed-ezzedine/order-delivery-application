package com.example.orderdelivery.order.store;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<OrderEntity, String> {
    List<OrderEntity> getAllByStatusIs(String status);
}
