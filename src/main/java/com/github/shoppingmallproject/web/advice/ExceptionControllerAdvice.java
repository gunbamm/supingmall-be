package com.github.shoppingmallproject.web.advice;


import com.github.shoppingmallproject.service.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(NotAcceptException.class)//처리할 수 없는 요청
    public String handleNotAcceptException(NotAcceptException n){
        return n.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)//찾을 수 없을때
    public String handleNotFoundException(NotFoundException n){
        return n.getMessage();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomBindException.class)//양식과 맞지 않을때
    public String handleCustomBindException(CustomBindException n){
        return n.getMessage();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)//입력한 키가 이미 존재할때
    public String handleDuplicateKeyException(DuplicateKeyException d){
        return d.getMessage();
    }
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDenied.class)//권한이 없을때
    public String handleNotAccessDenied(AccessDenied n){
        return n.getMessage();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccountLockedException.class)//계정이 잠겼을때
    public String handleAccountLockedException(AccountLockedException n){
        return n.getMessage();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)//비밀번호가 틀렸을때
    public String handleBadCredentialsException(BadCredentialsException b){
        return b.getMessage();
    }
}
