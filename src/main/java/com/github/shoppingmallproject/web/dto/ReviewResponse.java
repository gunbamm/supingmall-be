package com.github.shoppingmallproject.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Integer reviewId;
    private String reviewContents;
    private LocalDateTime createAt;
    private Integer score;
    private Integer productId;
    private String productName;
    private String photoUrl;
    private String photoType;
}
