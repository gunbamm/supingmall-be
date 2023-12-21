package com.github.shoppingmallproject.service.mappers;

import com.github.shoppingmallproject.repository.order.OrderEntity;
import com.github.shoppingmallproject.web.dto.product.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "orderAt", expression = "java(formatting(orderEntity.getOrderAt()))")
    OrderResponse orderEntityToOrderResponse(OrderEntity orderEntity);

    default String formatting(LocalDateTime localDateTime){
        if( localDateTime != null ){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 - HH시 mm분");
            return localDateTime.format(dateTimeFormatter);
        }else return null;
    }
}
