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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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


        // productId 값은 화면에서 넘어올건데, 잘못된 값일 넘어올 경우의 처리는 안하였음.
        if (userEntity != null) {
            Optional<ProductEntity> productEntity = Optional.ofNullable(productJpa.findById(productId)
                    .orElseThrow(() -> new NotFoundException("유효하지 않은 상품번호입니다.")));

            ReviewEntity createdReview = reviewJpa.save(
                    ReviewEntity.builder()
                            .reviewContents(reviewEntity.getReviewContents())
                            .createAt(LocalDateTime.now())
                            .score(reviewEntity.getScore())
                            .productEntity(productEntity.orElse(null))
                            .userEntity(userEntity)
                            .build());

            if (createdReview != null) {
                // 저장 성공
                result.put("code", "S");
                result.put("message", "리뷰 등록 성공");
            } else {
                // 저장 실패
                result.put("code", "E");
                result.put("message", "리뷰 등록 실패");
            }

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

                ReviewEntity updatedReview = reviewJpa.save(review);

                if (updatedReview != null) {
                    // 저장 성공
                    result.put("code", "S");
                    result.put("message", "리뷰 수정 성공");
                } else {
                    // 저장 실패
                    result.put("code", "E");
                    result.put("message", "리뷰 수정 실패");
                }
            }

        }

        return result;
    }

}
