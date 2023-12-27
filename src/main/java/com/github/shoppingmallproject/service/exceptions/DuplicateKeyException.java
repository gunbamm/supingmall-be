package com.github.shoppingmallproject.service.exceptions;

import lombok.Getter;

@Getter
public class DuplicateKeyException extends RuntimeException{

    private String code;
    private String request;

    public DuplicateKeyException(String code, String message, String request) {
        super(message);
        this.code = code;
        this.request = request;
    }
    public DuplicateKeyException(String message) {
        super(message);
    }

    public DuplicateKeyException(String key, String message) {
        super("이미 입력하신 \""+key+"\" "+message+" 가입된 계정이 있습니다.");
    }
}
