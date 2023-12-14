package com.github.shoppingmallproject.config.security;

import com.github.shoppingmallproject.service.SignUpLoginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BadCredentialsListener {
    private final SignUpLoginService signUpLoginService;

        @EventListener
    public void handleBadCredentials(AuthenticationFailureBadCredentialsEvent event){
            signUpLoginService.failureCount(event);
    }
}
