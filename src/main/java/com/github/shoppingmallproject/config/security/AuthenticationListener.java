package com.github.shoppingmallproject.config.security;

import com.github.shoppingmallproject.service.authAccount.AccountLockService;
import com.github.shoppingmallproject.service.exceptions.AccountLockedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthenticationListener {
    private final AccountLockService accountLockService;

    @EventListener
    public void handleBadCredentials(AuthenticationFailureBadCredentialsEvent event){
        Map<String, String> result = accountLockService.failureCount(event);
        if(result.get("request")!=null){
            if(result.get("request").equals("unlock")){
                accountLockService.resetFailureCount(event.getAuthentication().getName());
                result = accountLockService.failureCount(event);
                throw new BadCredentialsException(5-Integer.parseInt(result.get("remaining"))
                        +"번 틀렸습니다. "+"남은 횟수 : "+result.get("remaining"));
            }else if(result.get("request").equals("increment")) {
                if (result.get("remaining").equals("1")){
                    throw new BadCredentialsException(5-Integer.parseInt(result.get("remaining"))
                            +"번 틀렸습니다. "+"남은 횟수 : "+result.get("remaining")
                            +"\n한번 더 로그인 실패시 "+event.getAuthentication().getName()
                            +" 계정이 5분간 잠깁니다.");
                }
                throw new BadCredentialsException(5-Integer.parseInt(result.get("remaining"))
                        +"번 틀렸습니다. "+"남은 횟수 : "+result.get("remaining"));
            } else if(result.get("request").equals("locked")){
//                throw new AccountLockedException(result.get("name"),result.get("minute"),result.get("seconds"));
                throw new AccountLockedException("ACL", "Locked User", "dd");
            }
        }
    }
    @EventListener
    public void handleSuccessEvent(AuthenticationSuccessEvent event){
        String email = event.getAuthentication().getName();
        accountLockService.resetFailureCount(email);
    }
}
