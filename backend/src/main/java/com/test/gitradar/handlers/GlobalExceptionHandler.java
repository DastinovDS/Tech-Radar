package com.test.gitradar.handlers;

import com.test.gitradar.exceptions.BaseException;
import com.test.gitradar.models.ErrorResponseModel;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseModel> handleException(BaseException exception) {

        ResponseStatus statusAnnotation = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);

        HttpStatus status = statusAnnotation != null ?
                statusAnnotation.value()
                : HttpStatus.BAD_REQUEST;

        ErrorResponseModel errorResponseModel = new ErrorResponseModel();
        errorResponseModel.setMessage(exception.getMessage());
        errorResponseModel.setErrorMessage(exception.getErrorSource());
        errorResponseModel.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponseModel, status);
    }
}
