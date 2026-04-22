package com.test.gitradar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RepositoryForbiddenException extends RuntimeException {
    public RepositoryForbiddenException() {
        super("Do not have permission to access repository");
    }
}
