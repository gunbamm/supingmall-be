package com.github.shoppingmallproject.web.controller;

import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.service.AccountService;
import com.github.shoppingmallproject.web.dto.AccountDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
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
    @PostMapping("/withdrawal")
    public String withdrawal(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return accountService.withdrawal(customUserDetails);
    }

    @PostMapping("/set-super-user")
    public String setSuperUser(HttpServletRequest httpServletRequest){
        return accountService.setSuperUser(httpServletRequest.getParameter("email"));
    }

}
