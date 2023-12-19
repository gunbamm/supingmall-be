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
public class CartResponse {
    private Integer productId;
    private Integer optionId;
    private String productName;
    private List<String> productImg;
    private String color;
    private String size;
    private Integer quantity;
}
