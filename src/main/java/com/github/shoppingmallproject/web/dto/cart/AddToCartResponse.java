package com.github.shoppingmallproject.web.dto.cart;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddToCartResponse {

    private Integer productOptionId;
    private Integer addAmount;
    private Integer totalAmount;
    private String message;
}
