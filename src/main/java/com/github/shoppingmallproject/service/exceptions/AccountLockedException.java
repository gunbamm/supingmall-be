package com.github.shoppingmallproject.service.exceptions;

import lombok.Getter;

@Getter
public class AccountLockedException extends RuntimeException{

    private String code;
    private String request;

    public AccountLockedException(String code, String message, String request) {
        super(message);
        this.code = code;
        this.request = request;
    }
//    public AccountLockedException(String message) {
//        super(message);
//    }

//    public AccountLockedException( String name, String minute, String seconds){
//        super(String.format(
//                "\"%s\"님의 계정이 비밀번호 5회 실패로 잠겼습니다.\n" +
//                "남은 시간 : %s분 %s초", name, minute, seconds)
//        );
//    }
}
