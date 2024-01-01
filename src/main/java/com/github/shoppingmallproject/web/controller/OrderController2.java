package com.github.shoppingmallproject.web.controller;

import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.service.OrderService2;
import com.github.shoppingmallproject.web.dto.OrderDTO2;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/account")
@RequiredArgsConstructor
public class OrderController2 {
    private final OrderService2 orderService2;

    @PostMapping("/orders")
    public boolean createOrder(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody OrderDTO2 orderDTO2){
        return orderService2.createOrder(customUserDetails, orderDTO2);
    }

}
