package com.github.shoppingmallproject.web.controller;

import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.service.SignUpLoginService;
import com.github.shoppingmallproject.web.dto.AccountDTO;
import com.github.shoppingmallproject.web.dto.LoginRequest;
import com.github.shoppingmallproject.web.dto.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SignUpLoginController {
    private final SignUpLoginService signUpLoginService;
    @PostMapping("/sign-up")
    public String signUp(@RequestBody SignUpRequest signUpRequest){
        return signUpLoginService.signUp(signUpRequest);
    }

    @GetMapping("/check-email")
    public boolean checkEmail(HttpServletRequest httpServletRequest){
        return signUpLoginService.checkEmail(httpServletRequest.getParameter("email"));
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        List<String> tokenAndUserName = signUpLoginService.login(loginRequest);
        httpServletResponse.setHeader("Token", tokenAndUserName.get(0));
        return "\""+tokenAndUserName.get(1)+"\"님 환영합니다.";
    }


    @GetMapping("/login")
    public String logoutDirection(){
        return "로그인화면";
    }//임시 필요없음


}
