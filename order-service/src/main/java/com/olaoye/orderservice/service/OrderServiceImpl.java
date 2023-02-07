package com.olaoye.orderservice.service;

import brave.Span;
import brave.Tracer;
import com.olaoye.orderservice.dto.InventoryResponse;
import com.olaoye.orderservice.dto.OrderLineItemsDto;
import com.olaoye.orderservice.dto.OrderRequest;
import com.olaoye.orderservice.events.OrderPlacedEvents;
import com.olaoye.orderservice.model.Order;
import com.olaoye.orderservice.model.OrderLineItems;
import com.olaoye.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;//this is for creating separate trace ID Since Inventory call will be running on separate thread
    private final KafkaTemplate<String, OrderPlacedEvents> kafkaTemplate;

    @Override
    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderNumber(UUID.randomUUID().toString().split("-")[0]);
        order.setOrderLineItemsList(orderLineItems);


        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookUp");

        try(Tracer.SpanInScope inventoryLook = tracer.withSpanInScope(inventoryServiceLookup.start())){
            inventoryServiceLookup.tag("call", "inventory-service");
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)//by default webClient will make asynchronous call but we have to make it synchronous by calling .block
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::getIsInStock);
        if (allProductsInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvents(order.getOrderNumber()));
            return "Order place successfully";
        } else {
            throw new IllegalArgumentException("Product is not in stock please check back later");
        }
        } finally {
            inventoryServiceLookup.flush();
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }
}
