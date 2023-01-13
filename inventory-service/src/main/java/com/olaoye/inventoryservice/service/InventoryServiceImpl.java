package com.olaoye.inventoryservice.service;

import com.olaoye.inventoryservice.dto.InventoryResponse;
import com.olaoye.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows//this is not advisable in production
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("wait started");
//        Thread.sleep(10000);
        log.info("wait ended");
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build()
                ).toList();
    }
}
