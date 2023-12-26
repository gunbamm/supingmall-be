package com.github.shoppingmallproject.service.mappers;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.product.ProductOption;
import com.github.shoppingmallproject.repository.product.ProductPhoto;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.web.dto.product.OptionDTO;
import com.github.shoppingmallproject.web.dto.product.PhotoDTO;
import com.github.shoppingmallproject.web.dto.product.ProductDetailResponse;
import com.github.shoppingmallproject.web.dto.product.SaleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "createAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "finishAt", expression = "java(strToTimeFormat(saleRequest.getFinishAt()))")
    @Mapping(target = "userEntity", source = "userEntity")
    @Mapping(target = "productOptions", source = "productOption")
    @Mapping(target = "productPhotos", source = "productPhoto")
    @Mapping(target = "productStatus", expression = "java(\"판매중\")")
    ProductEntity saleRequestToProductEntity
            (SaleRequest saleRequest, UserEntity userEntity, List<ProductOption> productOption, List<ProductPhoto> productPhoto);

    @Mapping(target = "createAt", expression = "java(formatting(productEntity.getCreateAt()))")
    @Mapping(target = "finishAt", expression = "java(formatting(productEntity.getFinishAt()))")
    ProductDetailResponse productEntityToProductDetailResponse(ProductEntity productEntity);

    ProductOption OptionDTOToProductOption(OptionDTO optionDTO);

    ProductPhoto PhotoDTOToProductPhoto(PhotoDTO photoDTO);

    default LocalDateTime strToTimeFormat(String finishAt){
            return LocalDateTime.parse(finishAt, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    default String formatting(LocalDateTime localDateTime){
        if( localDateTime != null ){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 - HH시 mm분");
            return localDateTime.format(dateTimeFormatter);
        }else return null;
    }
}
