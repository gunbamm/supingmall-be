package com.github.shoppingmallproject.service.exceptions;

import lombok.Getter;

@Getter
public class NotAcceptException extends RuntimeException{

    private String code;
    private String request;

    public NotAcceptException(String code, String message, String request) {
        super(message);
        this.code = code;
        this.request = request;
    }
    public NotAcceptException(String message) {
        super(message);
    }


}
