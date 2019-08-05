package com.gaminho.oacproject.web;

import com.gaminho.oacproject.error.OACApiError;
import com.gaminho.oacproject.error.exception.project.InvalidTypeException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class OACExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class)
    public static ResponseEntity<OACApiError> handleConstraintViolation(
            ConstraintViolationException ex) {

        // FIXME Bang
        OACApiError error = new OACApiError(
                "ConstraintViolationException",
                HttpStatus.BAD_REQUEST,
                ex.getCause().getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(value = InvalidTypeException.class)
    public static ResponseEntity<?> handleInvalidTypeException(InvalidTypeException exception){

        OACApiError error = new OACApiError(
                "InvalidTypeException",
                HttpStatus.BAD_REQUEST,
                exception.getMessage());

        return ResponseEntity.badRequest().body(error);
    }
}
