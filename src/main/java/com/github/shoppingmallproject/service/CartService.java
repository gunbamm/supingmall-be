package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.cart.CartEntity;
import com.github.shoppingmallproject.repository.cart.CartJpa;
import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import com.github.shoppingmallproject.repository.productOption.ProductOptionJpa;
import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.repository.users.UserJpa;
import com.github.shoppingmallproject.service.exceptions.NotFoundException;
import com.github.shoppingmallproject.service.mappers.CartMapper;
import com.github.shoppingmallproject.web.dto.cart.AddToCartResponse;
import com.github.shoppingmallproject.web.dto.cart.CartDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartJpa cartJpa;
    private final UserJpa userJpa;
    private final ProductOptionJpa productOptionJpa;

    // 장바구니 전체 조회
    public List<CartDTO> findAllCart() {
        List<CartEntity> cartEntities = cartJpa.findAll();
        if (cartEntities.isEmpty()) throw new NotFoundException("카트에 담긴 상품이 없습니다.");
        return cartEntities.stream().map(CartMapper.INSTANCE::cartEntityToCartDTO).collect(Collectors.toList());
    }

//    // 장바구니 담기
//
//    public AddToCartResponse addToCart
//            (CustomUserDetails customUserDetails, Integer productOptionId, Integer addAmount) {
//        UserEntity userEntity = userJpa.findByEmail(customUserDetails.getUsername());
//
//        ProductOptionEntity productOptionEntity = productOptionJpa.findById(productOptionId)
//                .orElseThrow( () -> new NotFoundException("해당되는 상품이 없습니다."));
//
//        if (productOptionEntity.getStock() < addAmount)
//            throw new NotFoundException("현재 상품의 재고가 없습니다.");
//
//        Integer totalCartAmount = 0;
//
//        CartEntity savedCartEntity =
//                cartJpa.findByUserAndProductOptionEntity(userEntity, productOptionEntity);
//
//        if (savedCartEntity == null) {
//            CartEntity cartEntity = CartEntity.builder()
//                    .productOptionEntity(productOptionEntity)
//                    .cartAmount(addAmount)
//                    .user(userEntity)
//                    .build();
//
//            cartJpa.save(cartEntity);
//            totalCartAmount = cartEntity.getCartAmount();
//        } else {
//            Integer cartAmountAndProductAmount = savedCartEntity.getCartAmount() + addAmount;
//            if (cartAmountAndProductAmount > productOptionEntity.getStock())
//                throw new NotFoundException("현재 상품의 재고가 없습니다.");
//
//            CartEntity cartEntity = CartEntity.builder()
//                    .cartId(savedCartEntity.getCartId())
//                    .productOptionEntity(productOptionEntity)
//                    .user(userEntity)
//                    .cartAmount(cartAmountAndProductAmount)
//                    .build();
//            cartJpa.save(cartEntity); // 업데이트
//            totalCartAmount = cartEntity.getCartAmount();
//        }
//
//        return new AddToCartResponse(productOptionId, addAmount, totalCartAmount, "장바구니에 성공적으로 담았습니다.");
//
//
//    }

    // 장바구니 담기 ( 중복된 코드 생략 )
    public AddToCartResponse addToCart(CustomUserDetails customUserDetails, Integer productOptionId, Integer addAmount) {
        UserEntity userEntity = userJpa.findByEmail(customUserDetails.getUsername());

        ProductOptionEntity productOptionEntity = productOptionJpa.findById(productOptionId)
                .orElseThrow(() -> new NotFoundException("해당되는 상품이 없습니다."));

        if (productOptionEntity.getStock() < addAmount)
            throw new NotFoundException("현재 상품의 재고가 없습니다.");

        CartEntity savedCartEntity = cartJpa.findByUserAndProductOptionEntity(userEntity, productOptionEntity);

        Integer totalCartAmount;

        if (savedCartEntity == null) {
            totalCartAmount = saveCart(userEntity, productOptionEntity, addAmount);
        } else {
            totalCartAmount = updateCart(savedCartEntity, productOptionEntity, addAmount);
        }

        return new AddToCartResponse(productOptionId, addAmount, totalCartAmount, "장바구니에 성공적으로 담았습니다.");
    }

    private Integer saveCart(UserEntity userEntity, ProductOptionEntity productOptionEntity, Integer addAmount) {
        CartEntity cartEntity = CartEntity.builder()
                .productOptionEntity(productOptionEntity)
                .cartAmount(addAmount)
                .user(userEntity)
                .build();

        cartJpa.save(cartEntity);
        return cartEntity.getCartAmount();
    }

    private Integer updateCart(CartEntity savedCartEntity, ProductOptionEntity productOptionEntity, Integer addAmount) {
        Integer cartAmountAndProductAmount = savedCartEntity.getCartAmount() + addAmount;

        if (cartAmountAndProductAmount > productOptionEntity.getStock())
            throw new NotFoundException("현재 상품의 재고가 없습니다.");

        savedCartEntity.setCartAmount(cartAmountAndProductAmount);
        cartJpa.save(savedCartEntity); // 업데이트
        return savedCartEntity.getCartAmount();
    }


}
