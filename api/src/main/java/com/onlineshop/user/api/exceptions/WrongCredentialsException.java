package com.onlineshop.user.api.exceptions;

public class WrongCredentialsException extends RuntimeException{
    public WrongCredentialsException() {
        super("Wrong username or password");
    }
}
