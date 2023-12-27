package com.github.shoppingmallproject.web.controller;

import com.github.shoppingmallproject.service.OAuthService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private OAuthService oAuthService;

    @ResponseBody
    @GetMapping("/kakao/callback")
    public void kakaoCallback(@RequestParam String code) {
        String accessToken = oAuthService.getKakaoAccessToken(code);
        oAuthService.createKakaoUser(accessToken);

    }

}
