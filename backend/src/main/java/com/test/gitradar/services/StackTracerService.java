package com.test.gitradar.services;


public class StackTracerService {

    private StackTracerService(){};

    public static String getTrace(){

        String errorSource;

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if(stackTrace.length > 4){
            StackTraceElement caller = stackTrace[3];
            errorSource = String.format("%s.%s(line:%d)",
                    caller.getClassName(),
                    caller.getMethodName(),
                    caller.getLineNumber()
            );
        }
        else{
            errorSource = "Unknown Source";
        }
        return errorSource;
    }
}
