package com.example.orderdelivery.order.core;

import com.example.orderdelivery.item.core.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderWriteService, OrderReadService {

    private final OrderPersister persister;
    private final OrderFetcher fetcher;

    @Override
    public void orderCreated(String orderId) {
        Order order = Order.builder().id(orderId).status(OrderStatus.PREPARING).build();
        persister.save(order);
    }

    @Override
    public void orderOpened(String orderId) throws OrderDoesNotExistException {
        Order order = fetcher.fetch(orderId).orElseThrow(() -> new OrderDoesNotExistException(orderId));
        order.setStatus(OrderStatus.OPEN);
        persister.save(order);
    }

    @Override
    public void orderClosed(String orderId) throws OrderDoesNotExistException {
        Order order = fetcher.fetch(orderId).orElseThrow(() -> new OrderDoesNotExistException(orderId));
        order.setStatus(OrderStatus.CLOSE);
        persister.save(order);
    }

    @Override
    public void itemAddedToOrder(String orderId, String itemId, int quantity) throws OrderDoesNotExistException {
        Order order = fetcher.fetch(orderId).orElseThrow(() -> new OrderDoesNotExistException(orderId));

        itemContainedInOrder(itemId, order)
                .ifPresentOrElse(
                        item -> incrementItemQuantity(quantity, item),
                        () -> addNewItem(itemId, quantity, order));

        persister.save(order);
    }

    @Override
    public Order get(String id) throws OrderDoesNotExistException {
        return fetcher.fetch(id).orElseThrow(() -> new OrderDoesNotExistException(id));
    }

    private static Optional<Item> itemContainedInOrder(String itemId, Order order) {
        return order.getItems().stream().filter(i -> i.getId().equals(itemId)).findAny();
    }

    private static void addNewItem(String itemId, int quantity, Order order) {
        List<Item> items = order.getItems();
        items.add(Item.builder().id(itemId).quantity(quantity).build());
        order.setItems(items);
    }

    private static void incrementItemQuantity(int quantity, Item item) {
        item.setQuantity(item.getQuantity() + quantity);
    }
}
