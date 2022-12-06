package com.olaoye.productservice.service;

import com.olaoye.productservice.dto.ProductRequest;
import com.olaoye.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    List<ProductResponse> availableProducts();
}
