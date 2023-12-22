package com.github.shoppingmallproject.service.exceptions;

public class NotFoundException extends RuntimeException{

    private String code;
    private String category;

    public NotFoundException(String message){
        super(message);
    }
    public NotFoundException(String keyKor,String key) {
        super("해당 "+keyKor+" \""+key+"\"의 계정을 찾을 수 없습니다.");
    }

}

