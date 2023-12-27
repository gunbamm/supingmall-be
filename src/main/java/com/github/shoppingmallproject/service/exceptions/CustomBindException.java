package com.github.shoppingmallproject.service.exceptions;


import lombok.Getter;

@Getter
public class CustomBindException extends RuntimeException{
    private String code;
    private String request;

    public CustomBindException(String code, String message, String request) {
        super(message);
        this.code = code;
        this.request = request;
    }
    public CustomBindException(String message){
        super(message);
    }

}
