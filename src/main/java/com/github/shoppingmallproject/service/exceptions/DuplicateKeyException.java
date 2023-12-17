package com.github.shoppingmallproject.service.exceptions;

public class DuplicateKeyException extends RuntimeException{
    public DuplicateKeyException(String message) {
        super(message);
    }

    public DuplicateKeyException(String key, String message) {
        super("이미 입력하신 \""+key+"\" "+message+" 가입된 계정이 있습니다.");
    }
}
