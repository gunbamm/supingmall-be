package com.github.shoppingmallproject.web.controller;

import com.github.shoppingmallproject.service.ProductDetailsTest;
import com.github.shoppingmallproject.web.dto.product.ProductDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/product-details")
public class ProductDetailsTestController {

    private final ProductDetailsTest productDetailsTest;
    @GetMapping("/{productId}")
    public ProductDetailResponse getProductDetails(@PathVariable Integer productId){
        return productDetailsTest.getProductDetails(productId);
    }
}
