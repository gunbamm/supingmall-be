package com.github.shoppingmallproject.repository.product;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SaleStatusEntity {
    private Integer productOptionId;
    private String photoUrl;
    private String productName;
    private String productColor;
    private String productSize;
    private Integer productPrice;
    private Integer productStock;  // 변경: stock -> productStock
    private ProductEntity.Category category;
    private LocalDateTime productFinish;  // 변경: finishAt -> productFinish

    public SaleStatusEntity(Integer productOptionId, String photoUrl, String productName,String color, String productSize, Integer productPrice, Integer productStock, ProductEntity.Category category, LocalDateTime productFinish) {
        this.productOptionId = productOptionId;
        this.photoUrl = photoUrl;
        this.productName = productName;
        this.productColor = color;
        this.productSize = productSize;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.category = category;
        this.productFinish = productFinish;
    }
}
