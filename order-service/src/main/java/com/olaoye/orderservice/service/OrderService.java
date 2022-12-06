package com.olaoye.orderservice.service;

import com.olaoye.orderservice.dto.OrderRequest;

public interface OrderService {
    void placeOrder (OrderRequest orderRequest);
}
