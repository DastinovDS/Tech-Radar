package com.test.gitradar.exceptions;

public class EmtpyUrlException extends BaseException {
    public EmtpyUrlException() {
        super("Given url is empty");
    }
}
