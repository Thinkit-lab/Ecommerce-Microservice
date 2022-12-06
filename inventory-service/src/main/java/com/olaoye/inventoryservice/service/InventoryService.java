package com.olaoye.inventoryservice.service;

import com.olaoye.inventoryservice.dto.InventoryResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> skuCode);
}
