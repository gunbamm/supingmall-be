package com.github.shoppingmallproject.service.exceptions.response;

import lombok.Getter;

@Getter
public class ErrorRequestResponse {
    private String code;
    private String message;
    private String request;

    public ErrorRequestResponse(String code, String message, String request) {
        this.code = code;
        this.message = message;
        this.request = request;
    }
}
