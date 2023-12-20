package com.github.shoppingmallproject.service.mappers;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.product.ProductJoinProductPhoto;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoEntity;
import com.github.shoppingmallproject.web.dto.findProductDTO.ProductList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);


    @Mapping(target = "productId", source = "productId")
    ProductList productAndProductPhotoToProductList(ProductJoinProductPhoto productJoinProductPhoto);

}
