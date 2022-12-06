package com.olaoye.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Builder
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
}
