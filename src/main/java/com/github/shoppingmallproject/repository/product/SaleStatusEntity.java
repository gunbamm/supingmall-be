package com.github.shoppingmallproject.repository.product;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
