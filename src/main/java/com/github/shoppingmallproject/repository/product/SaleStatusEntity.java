package com.github.shoppingmallproject.repository.product;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SaleStatusEntity {

    private String photoUrl;
    private String productName;
    private Integer productPrice;
    private Integer productStock;  // 변경: stock -> productStock
    private ProductEntity.Category category;
    private LocalDateTime productFinish;  // 변경: finishAt -> productFinish

    public SaleStatusEntity(String photoUrl, String productName, Integer productPrice, Integer productStock, ProductEntity.Category category, LocalDateTime productFinish) {
        this.photoUrl = photoUrl;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.category = category;
        this.productFinish = productFinish;
    }
}
