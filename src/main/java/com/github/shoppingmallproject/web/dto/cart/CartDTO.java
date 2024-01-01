package com.github.shoppingmallproject.web.dto.cart;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CartDTO {
    private Integer cartId;
    private Integer productOptionId;
    private Integer userId;
    private Integer cartAmount;
}
