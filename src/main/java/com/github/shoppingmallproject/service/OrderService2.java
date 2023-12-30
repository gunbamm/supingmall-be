package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.order.OrderEntity;
import com.github.shoppingmallproject.repository.order.OrderJpa;
import com.github.shoppingmallproject.repository.order.OrderProductOption;
import com.github.shoppingmallproject.repository.orderItem.OrderItemEntity;
import com.github.shoppingmallproject.repository.orderItem.OrderItemJpa;
import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import com.github.shoppingmallproject.repository.productOption.ProductOptionJpa;
import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.repository.users.UserJpa;
import com.github.shoppingmallproject.service.exceptions.CustomBindException;
import com.github.shoppingmallproject.service.exceptions.NotFoundException;
import com.github.shoppingmallproject.service.mappers.OrderMapper;
import com.github.shoppingmallproject.web.dto.OrderDTO2;
import com.github.shoppingmallproject.web.dto.OrderItemDTO2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService2 {
    private final OrderJpa orderJpa;
    private final OrderItemJpa orderItemJpa;
    private final ProductOptionJpa productOptionJpa;
    private final UserJpa userJpa;

    @Transactional(transactionManager = "tm")
    public boolean createOrder(CustomUserDetails customUserDetails, OrderDTO2 orderDTO2) {
            List<OrderItemDTO2> orderItemDTO2s = orderDTO2.getOrderItemDTO2s();
            List<OrderProductOption> orderProductOptions= new ArrayList<>();

            for(OrderItemDTO2 orderItemDTO:orderItemDTO2s){
                OrderProductOption orderProductOption = OrderProductOption.builder()
                        .productOptionEntity(productOptionJpa.findById(orderItemDTO.getProductOptionId())
                                .orElseThrow(()-> new NotFoundException("NFE","옵션 상품을 찾을 수 없습니다.",String.valueOf(orderItemDTO.getProductOptionId()))))
                        .amount(orderItemDTO.getAmount())
                        .build();
                orderProductOptions.add(orderProductOption);
            }
            Integer totalPrice = null;

            for(OrderProductOption orderProductOption:orderProductOptions){
                ProductOptionEntity productOptionEntity = orderProductOption.getProductOptionEntity();
                Integer setStock = productOptionEntity.getStock()-orderProductOption.getAmount();
                if(setStock<0) throw new CustomBindException("CBE",productOptionEntity.getProductEntity().getProductName()+", "
                        +productOptionEntity.getColor()+", "+productOptionEntity.getProductSize()+" 의 재고가 구매수량 보다 적습니다.",
                        "재고 : "+productOptionEntity.getStock() +" 구매 수량 : "+orderProductOption.getAmount());
                productOptionEntity.setStock(setStock);
                totalPrice = (totalPrice!=null)?totalPrice+productOptionEntity.getProductEntity().getProductPrice()*orderProductOption.getAmount()
                        :productOptionEntity.getProductEntity().getProductPrice()*orderProductOption.getAmount();
            }
            UserEntity userEntity = userJpa.findByEmail(customUserDetails.getUsername());

            String recipient = (orderDTO2.getName()!=null)?orderDTO2.getName():customUserDetails.getUsername();
            String deliveryAddress = (orderDTO2.getShip()!=null)?orderDTO2.getShip():customUserDetails.getAddress();
            String orderRequest = orderDTO2.getOrderRequest();

            orderDTO2.setShip(deliveryAddress);
            orderDTO2.setName(recipient);
            orderDTO2.setOrderRequest(orderRequest);

            OrderEntity orderEntity = OrderMapper.INSTANCE.orderDTO2ToOrderEntity(orderDTO2, userEntity, totalPrice);
            orderJpa.save(orderEntity);
            for(OrderProductOption orderProductOption:orderProductOptions){
                OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                        .orderTable(orderEntity)
                        .productOptionEntity(orderProductOption.getProductOptionEntity())
                        .itemAmount(orderProductOption.getAmount())
                        .orderPrice(orderProductOption.getProductOptionEntity().getProductEntity().getProductPrice())
                        .build();
                orderItemJpa.save(orderItemEntity);
            }

            return true;
    }

}
