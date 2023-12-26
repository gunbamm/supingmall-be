package com.github.shoppingmallproject.web.dto.product;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductResponse {
    private Integer productId;
    private String productName;
    private List<String> productImg;
    private List<ProductOptionResponse> productOptionResponse;
    private Integer productTotalPrice;
}
