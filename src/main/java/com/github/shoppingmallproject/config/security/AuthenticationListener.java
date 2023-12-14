package com.github.shoppingmallproject.config.security;

import com.github.shoppingmallproject.service.AccountLockService;
import com.github.shoppingmallproject.service.exceptions.NotAcceptException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AuthenticationListener {
    private final AccountLockService accountLockService;

    @EventListener
    public void handleBadCredentials(AuthenticationFailureBadCredentialsEvent event){
        String result = accountLockService.failureCount(event);
            if(result!=null){
                throw new NotAcceptException(result);
            }
    }
    @EventListener
    public void handleSuccessEvent(AuthenticationSuccessEvent event){
        accountLockService.resetFailureCount(event);
    }
}
