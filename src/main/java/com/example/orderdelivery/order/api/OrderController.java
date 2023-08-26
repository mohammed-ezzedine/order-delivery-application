package com.example.orderdelivery.order.api;

import com.example.orderdelivery.order.core.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderCreator orderCreator;
    private final OrderReadService orderReadService;
    private final OrderNotifier orderNotifier;

    @PostMapping
    public ResponseEntity<OrderCreationApiResponse> createOrder() {
        log.info("Received a request to create an order");
        String orderId = orderCreator.createOrder();
        OrderCreationApiResponse response = OrderCreationApiResponse.builder().id(orderId).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderApiModel> getOrder(@PathVariable String id) throws OrderDoesNotExistException {
        log.info("Received a request to get the details for order with ID {}", id);
        Order order = orderReadService.get(id);
        OrderApiModel orderApiModel = OrderApiModel.builder().id(order.getId()).status(order.getStatus().name()).items(order.getItems()).build();
        return ResponseEntity.ok(orderApiModel);
    }

    @PostMapping("{id}/items")
    public ResponseEntity<Void> addItemToOrder(@PathVariable String id, @RequestBody AddItemToOrderApiRequest request) throws OrderDoesNotExistException {
        log.info("Received a request to add item to order with ID {}, request={}", id, request);
        AddItemToOrderRequest orderRequest = AddItemToOrderRequest.builder().itemId(request.getItemId()).quantity(request.getItemQuantity()).orderId(id).build();
        orderNotifier.addItemToOrder(orderRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}/close")
    public ResponseEntity<Void> closeOrder(@PathVariable String id) throws OrderDoesNotExistException {
        log.info("Received a request to close order with ID {}", id);
        orderNotifier.closeOrder(id);
        return ResponseEntity.ok().build();
    }
}
