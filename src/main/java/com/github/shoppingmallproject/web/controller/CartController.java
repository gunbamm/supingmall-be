package com.github.shoppingmallproject.web.controller;

import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.service.CartService;
import com.github.shoppingmallproject.web.dto.cart.AddToCartResponse;
import com.github.shoppingmallproject.web.dto.cart.CartDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/")
public class CartController {
    private final CartService cartService;

    // 장바구니 조회
    @GetMapping("/cart")
    public List<CartDTO> findAllCart(){
        List<CartDTO> cartDTOS = cartService.findAllCart();
        return cartDTOS;
    }

    // 장바구니 담기
    @PostMapping("/cart")
    public AddToCartResponse addToCart(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Integer productOptionId,
            @RequestParam Integer addAmount
            ){
        return cartService.addToCart(customUserDetails, productOptionId, addAmount);
    }

}
