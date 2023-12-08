package com.onlineshop.user.api.exceptions;

public class IdParseException extends RuntimeException {
    public IdParseException() {
        super("Failed to parse user id");
    }
}
