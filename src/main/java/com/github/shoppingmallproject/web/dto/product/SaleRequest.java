package com.github.shoppingmallproject.web.dto.product;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SaleRequest {
    private String productName;
    private String category;
    private String productPrice;
    private String finishAt;
    private List<PhotoDTO> photo;
    private List<OptionDTO> option;
}
