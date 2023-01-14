package com.olaoye.orderservice.service;

import com.olaoye.orderservice.dto.OrderRequest;

public interface OrderService {
    String placeOrder (OrderRequest orderRequest);
}
