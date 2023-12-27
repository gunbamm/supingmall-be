package com.github.shoppingmallproject.web.dto.findProductDTO;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.shoppingmallproject.repository.product.ProductJoinPhotoAndReview;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductResponse {
    private String code;
    private String message;
    private Page<ProductJoinPhotoAndReview> productList;
}
