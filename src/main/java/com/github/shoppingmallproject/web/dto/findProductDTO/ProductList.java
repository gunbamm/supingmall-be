package com.github.shoppingmallproject.web.dto.findProductDTO;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductList {
    private String productId;
    private String productName;
    private Integer productPrice;
    private String productPhoto;
    private String category;
    private String productStatus;
    private Float scoreAvg;
    private Integer reviewCount;
    private String create_at;
}
