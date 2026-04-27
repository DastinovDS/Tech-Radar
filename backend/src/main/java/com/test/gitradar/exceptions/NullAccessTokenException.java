package com.test.gitradar.exceptions;

public class NullAccessTokenException extends RuntimeException {
    public NullAccessTokenException() {
        super("Given access token is null or empty");
    }
}
