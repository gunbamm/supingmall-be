package com.github.shoppingmallproject.config;

import com.github.shoppingmallproject.service.SignUpLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountDataCleanScheduler {
    private final SignUpLoginService signUpLoginService;

    @Scheduled(cron = "0 0 0 * * 1") // 매주 월요일에 실행
    public void cleanupOldWithdrawnUser(){
        signUpLoginService.cleanupOldWithdrawnUser();
        log.info("탈퇴한지 7일 이상된 계정을 삭제 하였습니다.");
    }

}
