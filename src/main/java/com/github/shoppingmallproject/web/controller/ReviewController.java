package com.github.shoppingmallproject.web.controller;


import com.github.shoppingmallproject.repository.review.ReviewEntity;
import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.service.ReviewService;
import com.github.shoppingmallproject.web.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/list")
    public List<ReviewResponse> findReviewList(@AuthenticationPrincipal CustomUserDetails customUserDetails){

        return reviewService.findByUserEntity(customUserDetails);

    }

    @PostMapping("/{product_id}/createReview")
    public ResponseEntity<Map<String, String>> createReview(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("product_id") int productId,
            @RequestBody ReviewEntity reviewEntity) {

        Map<String, String> response;
        response = reviewService.createReview(customUserDetails, productId, reviewEntity);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/{review_id}/updateReview")
    public ResponseEntity<Map<String, String>> updateReview(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("review_id") int reviewId,
            @RequestBody ReviewEntity reviewEntity) {

        Map<String, String> response;
        response = reviewService.updateReview(customUserDetails, reviewId, reviewEntity);

        return ResponseEntity.ok(response);

    }

}
