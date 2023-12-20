package com.github.shoppingmallproject.web.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class CartAndTotalQuantityResponse {
    private List<CartResponse> cartResponseList;
    private Integer totalQuantity;
}
