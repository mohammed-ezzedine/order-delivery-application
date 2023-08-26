package com.example.orderdelivery.order.core;

import java.util.List;
import java.util.Optional;

public interface OrderFetcher {
    Optional<Order> fetch(String id);
    boolean orderExists(String id);
    List<Order> getOpenOrder();
}
