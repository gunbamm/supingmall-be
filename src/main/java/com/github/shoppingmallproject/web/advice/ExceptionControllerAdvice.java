package com.github.shoppingmallproject.web.advice;


import com.github.shoppingmallproject.service.exceptions.AccessDenied;
import com.github.shoppingmallproject.service.exceptions.NotAcceptException;
import com.github.shoppingmallproject.service.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException n){
        return n.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(NotAcceptException.class)
    public String handleNotAcceptException(NotAcceptException n){
        return n.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(AccessDenied.class)
    public String handleNotAccessDenied(AccessDenied n){
        return n.getMessage();
    }

}
