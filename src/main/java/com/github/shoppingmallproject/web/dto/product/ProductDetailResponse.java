package com.github.shoppingmallproject.web.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDetailResponse {
    private Integer productId; // 프로덕트엔티티
    private String productName; // 프로덕트엔티티
//    private String photoUrl; // 프로덕트포토엔티티
    private Integer productPrice; // 프로덕트엔티티
    private String category; // 프로덕트엔티티
    private String productStatus;// 프로덕트엔티티
    private String createAt;// 프로덕트엔티티 //상품등록일
    private String finishAt;// 판매종료일
    // 별점 평균 대기
    private Double scoreAvg;
    private List<PhotoDTO> productPhoto;// 프로덕트포토엔티티
    private List<OptionDTO> productDetailList; // 프로덕트 옵션엔티티
    private List<ReviewDTO> productReview;
}