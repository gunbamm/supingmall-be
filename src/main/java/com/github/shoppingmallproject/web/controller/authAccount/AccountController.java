package com.github.shoppingmallproject.web.controller.authAccount;

import com.github.shoppingmallproject.repository.order.OrderItemEntity;
import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.service.authAccount.AccountService;
import com.github.shoppingmallproject.web.dto.authAccount.AccountDTO;
import com.github.shoppingmallproject.web.dto.product.CartAndTotalQuantityResponse;
import com.github.shoppingmallproject.web.dto.product.OrderResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/my-page")
    public AccountDTO getMyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return accountService.getMyInfo(customUserDetails);
    }

    @PatchMapping("/my-page")
    public AccountDTO patchMyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody AccountDTO accountDTO){
        return accountService.patchMyInfo(customUserDetails, accountDTO);
    }

    @PostMapping("/my-page/cart")
    public String cartAddItem(HttpServletRequest httpServletRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return accountService.cartAddItem(httpServletRequest.getParameter("option-id"),
                httpServletRequest.getParameter("quantity"),
                customUserDetails);
    }
    @GetMapping("/my-page/cart")
    public CartAndTotalQuantityResponse getCartItem(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return accountService.getCartItem(customUserDetails);
    }
    @GetMapping("/my-page/order-history")
    public List<OrderResponse> getMyOrder(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return accountService.getMyOrder(customUserDetails);
    }
    @PostMapping("/withdrawal")
    public String withdrawal(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return accountService.withdrawal(customUserDetails);
    }

    @PostMapping("/set-super-user")
    public String setSuperUser(HttpServletRequest httpServletRequest){
        return accountService.setSuperUser(httpServletRequest.getParameter("email"));
    }

}
