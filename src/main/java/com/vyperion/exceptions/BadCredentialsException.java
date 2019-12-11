package com.vyperion.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@NoArgsConstructor
public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(String exception) {
        super(exception);
    }
}
