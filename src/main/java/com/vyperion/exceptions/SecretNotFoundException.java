package com.vyperion.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@NoArgsConstructor
public class SecretNotFoundException extends RuntimeException {
    public SecretNotFoundException(String exception) {
        super(exception);
    }
}
