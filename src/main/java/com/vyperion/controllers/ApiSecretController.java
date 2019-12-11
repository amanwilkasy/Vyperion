package com.vyperion.controllers;

import com.vyperion.config.jwt.JWTFilter;
import com.vyperion.dto.ApiSecret;
import com.vyperion.services.ApiSecretService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("secret")
public class ApiSecretController {

    private final ApiSecretService apiSecretService;
    private final JWTFilter jwtFilter;

    public ApiSecretController(ApiSecretService apiSecretService, JWTFilter jwtFilter) {
        this.apiSecretService = apiSecretService;
        this.jwtFilter = jwtFilter;
    }

    @GetMapping("get")
    public ResponseEntity<ApiSecret> getSecret() {
        return ResponseEntity.ok().body(apiSecretService.findApiSecretByUserId(
                jwtFilter.getClientUser().getId()));
    }

    @GetMapping("generate")
    public ResponseEntity<ApiSecret> generateSecret() {
        return ResponseEntity.ok().body(apiSecretService.generateApiSecret(
                jwtFilter.getClientUser().getId()));

    }

    @GetMapping("regenerate")
    public ResponseEntity<ApiSecret> regenerateSecret() {
        return ResponseEntity.ok().body(apiSecretService.regenerateApiSecret(
                jwtFilter.getClientUser().getId()));
    }

}













