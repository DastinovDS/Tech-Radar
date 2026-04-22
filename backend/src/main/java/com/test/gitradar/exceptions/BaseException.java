package com.test.gitradar.exceptions;

import com.test.gitradar.services.StackTracerService;

public class BaseException extends RuntimeException{

    private final String errorSource;

    public BaseException(String message) {

        super(message);

        this.errorSource = StackTracerService.getTrace();
    }

    public String getErrorSource(){
        return this.errorSource;
    }
}
