package com.github.shoppingmallproject.service;


import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.product.ProductJpa;
import com.github.shoppingmallproject.repository.review.ReviewEntity;
import com.github.shoppingmallproject.repository.review.ReviewJpa;
import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.repository.users.UserJpa;
import com.github.shoppingmallproject.service.exceptions.NotFoundException;
import com.github.shoppingmallproject.web.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final UserJpa userJpa;
    private final ReviewJpa reviewJpa;
    private final ProductJpa productJpa;

    public List<ReviewResponse> findByUserEntity(CustomUserDetails customUserDetails) {

        List<ReviewResponse> reviewResponse = null;

        // 유저정보로 등록된 리뷰가 있는지 확인
        UserEntity userEntity = userJpa.findByEmail(customUserDetails.getUsername());

        //test 용
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUserId(8);

        if (userEntity != null) {
            reviewResponse = reviewJpa.findByUserEntity(userEntity).stream()
                .map(item->{
                    return ReviewResponse.builder()
                            .reviewId(item.getReviewId())
                            .reviewContents(item.getReviewContents())
                            .createAt(item.getCreateAt())
                            .score(item.getScore())
                            .productId(item.getProductEntity().getProductId())
                            .productName(item.getProductEntity().getProductName())
                            .photoUrl(item.getProductEntity().getProductPhotoEntities().get(0).getPhotoUrl())
                            .photoType(String.valueOf(item.getProductEntity().getProductPhotoEntities().get(0).getPhotoType()))
                            .build();
                })
                .toList();
        } else {
            // exception 처리
//            ReviewEntity reviewEntity = reviewJpa.findAllByUserEntity(userEntity)
//                    .orElseThrow(()->new NotFoundException("등록한 리뷰가 없습니다."));
        }

        return reviewResponse;
    }

    public Map<String, String> createReview(CustomUserDetails customUserDetails, int productId, ReviewEntity reviewEntity) {

        Map<String,String> result = new HashMap<>();

        // 유효한 사용자인지 체크
        UserEntity userEntity = userJpa.findByEmail(customUserDetails.getUsername());
//        UserEntity userEntity = userJpa.findByEmail("test3@naver.com"); // TEST

        if (userEntity != null) {
            Optional<ProductEntity> productEntity = Optional.ofNullable(productJpa.findById(productId)
                    .orElseThrow(() -> new NotFoundException("유효하지 않은 상품번호입니다.")));

            reviewJpa.save(
                    ReviewEntity.builder()
                            .reviewContents(reviewEntity.getReviewContents())
                            .createAt(LocalDateTime.now())
                            .score(reviewEntity.getScore())
                            .productEntity(productEntity.orElse(null))
                            .userEntity(userEntity)
                            .build()
            );

            // 리뷰등록 후 상품테이블 rating 값 업데이트 하는 로직 호출
            this.updateRatingFromProduct(productId);

            // 저장 성공
            result.put("code", "S");
            result.put("message", "리뷰 등록 성공");

        }

        return result;
    }

    public Map<String, String> updateReview(CustomUserDetails customUserDetails, int reviewId, ReviewEntity reviewEntity) {

        Map<String,String> result = new HashMap<>();

        // 유효한 사용자인지 체크
        UserEntity userEntity = userJpa.findByEmail(customUserDetails.getUsername());
//        UserEntity userEntity = userJpa.findByEmail("test3@naver.com"); // TEST


        if (userEntity != null) {
            Optional<ReviewEntity> getReview = Optional.ofNullable(reviewJpa.findById(reviewId)
                    .orElseThrow(() -> new NotFoundException("유효하지 않은 리뷰번호입니다.")));

            if (getReview.isPresent()) {
                ReviewEntity review = getReview.get();

                review.setReviewContents(reviewEntity.getReviewContents());
                review.setScore(reviewEntity.getScore());

                reviewJpa.save(review);

                // 리뷰업데이트 후 상품테이블 rating 값 업데이트 하는 로직 호출
                this.updateRatingFromProduct(review.getProductEntity().getProductId());

                // 저장 성공
                result.put("code", "S");
                result.put("message", "리뷰 수정 성공");

            }

        }

        return result;
    }

    public void updateRatingFromProduct(Integer productId) {
        // 리뷰등록한 상품번호에 딸린 모든 리뷰를 가져옴
        List<ReviewEntity> reviewListByProductId = reviewJpa.findByProductId(
                ProductEntity.builder()
                        .productId(productId)
                        .build()
        );

        // 상품번호에 딸린 리뷰점수의 평균 값을 구함
        OptionalDouble averageScore = reviewListByProductId.stream()
                .mapToInt(ReviewEntity::getScore)
                .average();

        // 평균 값은 소수점 1자리까지 반올림하고, 상품번호로 상품테이블 조회하여 rating 값 업데이트 함
        if (averageScore.isPresent()) {
            double rating = averageScore.orElse(0.0);
            String formattedRating = String.format("%.1f", rating);

            Optional<ProductEntity> productInfo = productJpa.findById(productId);

            if (productInfo.isPresent()) {
                ProductEntity product = productInfo.get();
                product.setRating(Double.valueOf(formattedRating));
                productJpa.save(product);
            }
        }
    }

}
