package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.repository.users.UserJpa;
import com.github.shoppingmallproject.service.exceptions.NotAcceptException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Primary
@RequiredArgsConstructor
public class AccountLockService {
    private final UserJpa userJpa;

    @Transactional(transactionManager = "tm")
    public String failureCount(AuthenticationFailureBadCredentialsEvent event) {
        String email = event.getAuthentication().getName();
        UserEntity userEntity = userJpa.findByEmail(email);
        if(userEntity.getLockDate() != null &&
                LocalDateTime.now().isAfter(userEntity.getLockDate().plusMinutes(5))
        ) {
            userEntity.setFailureCount(0);
            userEntity.setStatus("normal");
        }
        if(userEntity.getFailureCount()<5) {
            userEntity.setFailureCount(userEntity.getFailureCount() + 1);
            userEntity.setLockDate(LocalDateTime.now());
            return null;
        } else {
            userEntity.setStatus("lock");
            userEntity.setLockDate(LocalDateTime.now());
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lockDateTime = userEntity.getLockDate();
            Duration duration = Duration.between(now, lockDateTime.plusMinutes(5));

            return String.format("\"%s\"님의 계정이 비밀번호 5회 실패로 잠겼습니다.\n" +
                            "남은 시간 : %s분 %s초", userEntity.getName(), duration.toMinutes()
                    , duration.minusMinutes(duration.toMinutes()).getSeconds());
        }
    }

    @Transactional(transactionManager = "tm")
    public void resetFailureCount(AuthenticationSuccessEvent event){
        String email = event.getAuthentication().getName();
        UserEntity userEntity = userJpa.findByEmail(email);

        userEntity.setFailureCount(0);
        userEntity.setLockDate(null);
        userEntity.setStatus("normal");
    }
}
