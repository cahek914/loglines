package org.kao.loglines.controller;

import org.kao.loglines.exception.GenericServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(GenericServiceException.class)
    public ResponseEntity<String> taskError(GenericServiceException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

}
