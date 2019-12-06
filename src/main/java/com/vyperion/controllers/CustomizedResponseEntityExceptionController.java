package com.vyperion.controllers;

import com.vyperion.exceptions.ApiKeyNotFoundException;
import com.vyperion.exceptions.ErrorDetails;
import com.vyperion.exceptions.SecretNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiKeyNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleApiKeyNotFoundException(ApiKeyNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SecretNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleSecretNotFoundException(SecretNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
