package com.github.shoppingmallproject.web.dto.product;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderResponse {

    private Integer orderId;
    private String orderStatus;
    private String ship;
    private String orderRequest;
    private String orderAt;
    private List<ProductResponse> productResponseList;

    private Integer orderTotalPrice;
}
