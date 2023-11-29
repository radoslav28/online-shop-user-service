package com.onlineshop.user.api.exceptions;

public class NotExistingUserException extends RuntimeException{
    public NotExistingUserException(String email) {
        super(String.format("User with '%s' email doesn't exist", email));
    }
}
