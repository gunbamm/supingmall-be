package com.github.shoppingmallproject.service.exceptions;

public class AccountLockedException extends RuntimeException{
    public AccountLockedException(String message) {
        super(message);
    }

    public AccountLockedException( String name, String minute, String seconds){
        super(String.format(
                "\"%s\"님의 계정이 비밀번호 5회 실패로 잠겼습니다.\n" +
                "남은 시간 : %s분 %s초", name, minute, seconds)
        );
    }
}
