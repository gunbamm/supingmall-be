package com.github.shoppingmallproject.repository.product;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SaleStatusEntity {
    private String photoUrl;
    private String productName;
    private Integer productPrice;
    private Integer stock;
    private String category;
    private LocalDateTime finishAt;

    public SaleStatusEntity(String photoUrl, String productName, Integer productPrice, Integer stock, ProductEntity.Category category, LocalDateTime finishAt) {
        this.photoUrl = photoUrl;
        this.productName = productName;
        this.productPrice = productPrice;
        this.stock = stock;
        this.category = String.valueOf(category);
        this.finishAt = finishAt;
    }
}
