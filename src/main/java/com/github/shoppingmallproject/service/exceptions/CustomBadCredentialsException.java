package com.github.shoppingmallproject.service.exceptions;

import lombok.Getter;

@Getter
public class CustomBadCredentialsException extends RuntimeException{

    private String code;
    private String request;

    public CustomBadCredentialsException(String code, String message, String request) {
        super(message);
        this.code = code;
        this.request = request;
    }
}
