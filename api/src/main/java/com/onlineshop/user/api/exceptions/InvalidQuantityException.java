package com.onlineshop.user.api.exceptions;

public class InvalidQuantityException extends RuntimeException {
    public InvalidQuantityException() {
        super("Invalid quantity");
    }
}
