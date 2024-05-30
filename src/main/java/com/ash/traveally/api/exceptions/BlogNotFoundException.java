package com.ash.traveally.api.exceptions;

public class BlogNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2;

    public BlogNotFoundException(String message) {
        super(message);
    }
}