package com.onlineshop.user.api.exceptions;

public class ServiceUnavailableException extends RuntimeException{
    public ServiceUnavailableException() {
        super("Unable to perform request. Try again later.");
    }
}
