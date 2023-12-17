package com.github.shoppingmallproject.service.exceptions;

public class AccessDenied extends RuntimeException{
    public AccessDenied(String message) {
        super(message);
    }
}
