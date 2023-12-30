package com.github.shoppingmallproject.repository.order;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class OrderProductOption {
    private ProductOptionEntity productOptionEntity;
    private Integer amount;
}
