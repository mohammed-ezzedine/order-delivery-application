package com.example.orderdelivery.order.store;

import com.example.orderdelivery.order.core.Order;
import com.example.orderdelivery.order.core.OrderFetcher;
import com.example.orderdelivery.order.core.OrderPersister;
import com.example.orderdelivery.order.core.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderStorageManager implements OrderPersister, OrderFetcher {

    private final OrderRepository repository;

    @Override
    public void save(Order order) {
        repository.save(toEntity(order));
    }

    @Override
    public Optional<Order> fetch(String id) {
        return repository.findById(id).map(OrderStorageManager::fromEntity);
    }

    @Override
    public boolean orderExists(String id) {
        return repository.existsById(id);
    }

    @Override
    public List<Order> getOpenOrder() {
        return repository.getAllByStatusIs(OrderStatus.OPEN.name()).stream().map(OrderStorageManager::fromEntity).toList();
    }

    private static OrderEntity toEntity(Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .status(order.getStatus().name())
                .items(order.getItems())
                .build();
    }

    private static Order fromEntity(OrderEntity entity) {
        return Order.builder()
                .id(entity.getId())
                .status(OrderStatus.valueOf(entity.getStatus()))
                .items(entity.getItems())
                .build();
    }
}
