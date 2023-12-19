package com.github.shoppingmallproject.web.controller.authAccount;

import com.github.shoppingmallproject.service.authAccount.SignUpLoginService;
import com.github.shoppingmallproject.web.dto.authAccount.LoginRequest;
import com.github.shoppingmallproject.web.dto.authAccount.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
