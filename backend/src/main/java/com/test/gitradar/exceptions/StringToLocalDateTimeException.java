package com.test.gitradar.exceptions;

public class StringToLocalDateTimeException extends BaseException {
    public StringToLocalDateTimeException() {
        super("Error parsing Github ISO date");
    }
}
