package com.github.shoppingmallproject.web.controller;

import com.github.shoppingmallproject.service.ProductDetailService;
import com.github.shoppingmallproject.web.dto.product.ProductDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/product-detail")
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    // path id(productId)로 상세조회

    @GetMapping("/{productId}")
    public ProductDetailResponse getProductDetails(@PathVariable Integer productId){
        return productDetailService.getProductDetails(productId);
    }

}
