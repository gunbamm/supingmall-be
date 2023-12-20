package com.github.shoppingmallproject.repository.product;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductJoinProductPhoto {
    private String productId;
    private Integer userId;
    private String productName;
    private Integer productPrice;
    private String category;
    private String productStatus;
    private String createAt;
    private String finishAt;
    private String photoUrl;
    //    private Float scoreAvg;
//    private Integer reviewCount;
}