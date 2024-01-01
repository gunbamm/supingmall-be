package com.github.shoppingmallproject.service.mappers;

import com.github.shoppingmallproject.repository.cart.CartEntity;
import com.github.shoppingmallproject.web.dto.cart.CartDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "cartAmount", source = "cartAmount")
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "productOptionId", source = "productOptionEntity.productOptionId")
    CartDTO cartEntityToCartDTO(CartEntity cartEntity);
}
