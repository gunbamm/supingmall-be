package com.github.shoppingmallproject.config.security;

import com.github.shoppingmallproject.service.AccountLockService;
import com.github.shoppingmallproject.service.exceptions.AccountLockedException;
import com.github.shoppingmallproject.service.exceptions.NotAcceptException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticationListener {
    private final AccountLockService accountLockService;

    @EventListener
    public void handleBadCredentials(AuthenticationFailureBadCredentialsEvent event){
        List<String> result = accountLockService.failureCount(event);
        if(result!=null&&result.contains("unlock")){
            accountLockService.resetFailureCount(event.getAuthentication().getName());
        }else if(result!=null){
                throw new AccountLockedException(result.get(0),result.get(1),result.get(2));
            }
    }
    @EventListener
    public void handleSuccessEvent(AuthenticationSuccessEvent event){
        String email = event.getAuthentication().getName();
        accountLockService.resetFailureCount(email);
    }
}
