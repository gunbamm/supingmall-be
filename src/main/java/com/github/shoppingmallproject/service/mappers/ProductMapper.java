package com.github.shoppingmallproject.service.mappers;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.product.ProductOption;
import com.github.shoppingmallproject.repository.product.ProductPhoto;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.web.dto.product.OptionDTO;
import com.github.shoppingmallproject.web.dto.product.PhotoDTO;
import com.github.shoppingmallproject.web.dto.product.SaleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
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

    ProductOption OptionDTOToProductOption(OptionDTO optionDTO);

    ProductPhoto PhotoDTOToProductPhoto(PhotoDTO photoDTO);

    default LocalDate strToTimeFormat(String finishAt){
            return LocalDate.parse(finishAt, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
