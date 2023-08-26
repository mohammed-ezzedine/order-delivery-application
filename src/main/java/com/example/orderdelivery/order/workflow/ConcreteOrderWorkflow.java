package com.example.orderdelivery.order.workflow;

import com.example.orderdelivery.order.core.OrderDoesNotExistException;
import com.example.orderdelivery.order.core.OrderWriteService;
import com.example.orderdelivery.order.workflow.event.request.AddItemToOrderWorkflowEvent;
import com.example.orderdelivery.order.workflow.event.request.OrderWorkflowRequestEvent;
import com.example.orderdelivery.order.workflow.event.request.OrderWorkflowRequestEventRepository;
import com.example.orderdelivery.order.workflow.event.response.ItemAddedToOrderWorkflowEvent;
import com.example.orderdelivery.order.workflow.event.response.OrderWorkflowResponseEvent;
import com.example.orderdelivery.order.workflow.event.response.OrderWorkflowResponseEventRepository;
import com.example.orderdelivery.order.workflow.task.AddItemToOrderTask;
import com.example.orderdelivery.order.workflow.task.OrderWorkflowTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Slf4j
@RequiredArgsConstructor
public class ConcreteOrderWorkflow implements OrderWorkflow {

    private final String orderId;
    private final OrderWriteService orderWriteService;
    private final OrderWorkflowRequestEventRepository requestEventRepository;
    private final OrderWorkflowResponseEventRepository responseEventRepository;

    private final BlockingQueue<OrderWorkflowTask> tasks = new ArrayBlockingQueue<>(1000);
    private boolean orderIsActive;

    @Override
    public void start(StartOrderWorkflowRequest request) {
        log.info("order workflow with id {} started", orderId);
        orderIsActive = true;

        replayHistory();

        do {
            try {
                OrderWorkflowTask task = tasks.take();
                handle(task);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        } while (orderIsActive);
    }

    @Override
    public void addItemToOrder(String itemId, int quantity) {
        log.info("Received a request to add item to order itemId={}, quantity={}", itemId, quantity);

        AddItemToOrderWorkflowEvent event = AddItemToOrderWorkflowEvent.builder()
                .itemId(itemId)
                .itemQuantity(quantity)
                .workflowId(orderId)
                .id(UUID.randomUUID().toString())
                .build();
        requestEventRepository.save(event);

        AddItemToOrderTask orderTask = AddItemToOrderTask.builder().itemId(itemId).itemQuantity(quantity).correlationId(event.getId()).build();
        tasks.add(orderTask);
    }

    @Override
    public void closeOrder() {
        orderIsActive = false;
        try {
            orderWriteService.orderClosed(orderId);
        } catch (OrderDoesNotExistException e) {
            throw new IllegalStateException();
        }
    }

    private void replayHistory() {
        List<OrderWorkflowRequestEvent> workflowRequestsHistory = requestEventRepository.findAllByWorkflowId(orderId);

        for (OrderWorkflowRequestEvent event : workflowRequestsHistory) {
            Optional<OrderWorkflowResponseEvent> optionalResponse = responseEventRepository.findByWorkflowIdAndCorrelationId(orderId, event.getId());
            if (optionalResponse.isEmpty()) {
                registerTask(event);
            }
        }
    }

    private void registerTask(OrderWorkflowRequestEvent event) {
        switch (event.getEventType()) {
            case AddItemToOrderWorkflowEvent.TYPE -> handleAddItemToOrderRequested((AddItemToOrderWorkflowEvent)event);
        }
    }

    private void handleAddItemToOrderRequested(AddItemToOrderWorkflowEvent event) {
        AddItemToOrderTask orderTask = AddItemToOrderTask.builder().itemId(event.getItemId()).itemQuantity(event.getItemQuantity()).correlationId(event.getId()).build();
        tasks.add(orderTask);
    }

    private void handle(OrderWorkflowTask task) {
        switch (task.getTaskType()) {
            case AddItemToOrderTask.TASK_TYPE -> handleAddItemToOrderTask((AddItemToOrderTask) task);
        }
    }

    private void handleAddItemToOrderTask(AddItemToOrderTask task) {
        log.info("Adding item to order with ID {}, item ID = {}, item quantity = {}", orderId, task.getItemId(), task.getItemQuantity());
        try {
            mimicSomeProcessing();
            orderWriteService.itemAddedToOrder(orderId, task.getItemId(), task.getItemQuantity());
            ItemAddedToOrderWorkflowEvent event = ItemAddedToOrderWorkflowEvent.builder().workflowId(orderId).id(UUID.randomUUID().toString()).correlationId(task.getCorrelationId()).build();
            responseEventRepository.save(event);
        } catch (OrderDoesNotExistException e) {
            throw new IllegalStateException();
        }
    }

    private static void mimicSomeProcessing() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
