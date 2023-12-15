package com.github.shoppingmallproject.service.exceptions;

public class AccountLockedException extends RuntimeException{
    public AccountLockedException(String message) {
        super(message);
    }
}
