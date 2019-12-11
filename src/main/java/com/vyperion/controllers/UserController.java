package com.vyperion.controllers;

import com.vyperion.config.jwt.JWTFilter;
import com.vyperion.dto.client.ResponseWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    private final JWTFilter jwtFilter;

    public UserController(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @GetMapping
    public ResponseWrapper getClientUser() {
        return new ResponseWrapper(jwtFilter.getClientUser());
    }
}







