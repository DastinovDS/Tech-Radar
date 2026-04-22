package com.test.gitradar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class GithubInternalException extends RuntimeException {
    public GithubInternalException() {
        super("GitHub not available");
    }
}
