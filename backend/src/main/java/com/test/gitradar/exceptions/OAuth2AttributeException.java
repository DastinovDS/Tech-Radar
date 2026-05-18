package com.test.gitradar.exceptions;

public class OAuth2AttributeException extends BaseException{
    public OAuth2AttributeException() {
        super("Invalid or missing attributes from OAuth2 provider");
    }
}
