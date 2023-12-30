package com.github.shoppingmallproject.service.mappers;

import com.github.shoppingmallproject.repository.order.OrderEntity;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.web.dto.OrderDTO2;
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

    @Mapping(target = "userEntity", source = "userEntity")
    @Mapping(target = "orderAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "orderStatus", expression = "java(\"접수\")")
    @Mapping(target = "ship", source = "orderDTO2.ship")
    @Mapping(target = "orderRequest", source = "orderDTO2.orderRequest")
    @Mapping(target = "orderTotalPrice", source = "totalPrice")
    OrderEntity orderDTO2ToOrderEntity(OrderDTO2 orderDTO2, UserEntity userEntity, Integer totalPrice);



    default String formatting(LocalDateTime localDateTime){
        if( localDateTime != null ){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 - HH시 mm분");
            return localDateTime.format(dateTimeFormatter);
        }else return null;
    }
}
