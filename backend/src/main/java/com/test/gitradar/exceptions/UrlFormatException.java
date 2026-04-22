package com.test.gitradar.exceptions;

public class UrlFormatException extends BaseException{
    public UrlFormatException() {
        super("Given url does not match correct format");
    }
}
