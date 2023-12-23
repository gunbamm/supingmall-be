package com.github.shoppingmallproject.service.exceptions;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{
    private String code;

    public BadRequestException(String code, String message) {
        super(message);
        this.code = code;
    }
}
