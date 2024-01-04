package com.ash.traveally.api.exceptions;

public class PlaceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public PlaceNotFoundException(String message) {
        super(message);
    }
}
