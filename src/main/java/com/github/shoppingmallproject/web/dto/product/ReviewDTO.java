package com.github.shoppingmallproject.web.dto.product;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Integer reviewId;
    private Integer userId;
    private Integer productId;
    private String nickName;
    private String reviewContents;
    private LocalDateTime createAt;
    private Integer score;
}
