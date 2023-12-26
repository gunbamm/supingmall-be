package com.github.shoppingmallproject.repository.product;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductJoinPhotoAndReview {
    private Integer productId;
    private Integer userId;
    private String productName;
    private Integer productPrice;
    private ProductEntity.Category category;
    private String createAt;
    private String photoUrl;
    private Integer reviewCount;
    private Float scoreAvg;

    private  static String formatting(LocalDateTime localDateTime) {
        if( localDateTime != null ){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 - HH시 mm분 ss초");
            return localDateTime.format(dateTimeFormatter);
        }else return null;
    }

    public ProductJoinPhotoAndReview(Integer productId, Integer userId, String productName, Integer productPrice, ProductEntity.Category category, LocalDateTime createAt, String photoUrl, Long reviewCount, Double scoreAvg) {
        this.productId = productId;
        this.userId = userId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.category = category;
        this.createAt = formatting(createAt);
        this.photoUrl = photoUrl;
        this.reviewCount = reviewCount.intValue();
        this.scoreAvg = scoreAvg != null ? scoreAvg.floatValue() : 0;
    }
}

