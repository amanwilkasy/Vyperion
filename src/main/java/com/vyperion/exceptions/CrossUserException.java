package com.vyperion.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CrossUserException extends RuntimeException {
    public CrossUserException(String exception) {
        super(exception);
    }
}
