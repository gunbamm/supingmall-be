package com.github.shoppingmallproject.service.exceptions;

import lombok.Getter;

@Getter
public class AccessDenied extends RuntimeException{
    private String code;
    private String request;

    public AccessDenied(String code, String message, String request) {
        super(message);
        this.code = code;
        this.request = request;
    }
    public AccessDenied(String message) {
        super(message);
    }
}
