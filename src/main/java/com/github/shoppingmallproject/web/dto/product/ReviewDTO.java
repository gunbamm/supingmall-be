package com.github.shoppingmallproject.web.dto.product;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private String nickName;
    private String reviewContents;
    private LocalDateTime createAt;
    private Integer score;
    private OptionDTO optionDTO;
}
