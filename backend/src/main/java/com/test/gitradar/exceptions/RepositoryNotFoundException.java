package com.test.gitradar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RepositoryNotFoundException extends RuntimeException{
    public RepositoryNotFoundException() {
        super("Repository was not found");
    }
}
