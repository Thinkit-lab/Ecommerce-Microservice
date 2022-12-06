package com.olaoye.productservice.controller;

import com.olaoye.productservice.dto.APIResponse;
import com.olaoye.productservice.dto.ProductRequest;
import com.olaoye.productservice.dto.ProductResponse;
import com.olaoye.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create-product")
    public ResponseEntity<APIResponse<?>> createProduct(@RequestBody ProductRequest productRequest){
        try {
           ProductResponse productResponse = productService.createProduct(productRequest);
           return new ResponseEntity(new APIResponse(true, "Product created successfully", productResponse), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity(new APIResponse(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all-products")
    public ResponseEntity<List<ProductResponse>> productsInStore() {
        List<ProductResponse> products = productService.availableProducts();
        return new ResponseEntity(new APIResponse(true, "List of Available Products", products), HttpStatus.OK);
    }
}
