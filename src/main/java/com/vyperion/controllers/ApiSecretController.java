package com.vyperion.controllers;

import com.vyperion.dto.ApiSecret;
import com.vyperion.exceptions.SecretNotFoundException;
import com.vyperion.services.ApiSecretService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("secret")
public class ApiSecretController {

    private final ApiSecretService apiSecretService;

    public ApiSecretController(ApiSecretService apiSecretService) {
        this.apiSecretService = apiSecretService;
    }

    @GetMapping("{id}/get")
    public ResponseEntity<ApiSecret> getSecret(@PathVariable String id){
        Optional<ApiSecret> apiSecret = apiSecretService.findApiSecretByUserId(id);
        return getSecretResponse(apiSecret, "No secret exists");
    }

    @GetMapping("{id}/generate")
    public ResponseEntity<ApiSecret> generateSecret(@PathVariable String id){
        Optional<ApiSecret> apiSecret = apiSecretService.generateApiSecret(id);
        return getSecretResponse(apiSecret, "generateSecret Error");
    }

    @GetMapping("{id}/regenerate")
    public ResponseEntity<ApiSecret> regenerateSecret(@PathVariable String id){
        Optional<ApiSecret> apiSecret =  apiSecretService.regenerateApiSecret(id);
        return getSecretResponse(apiSecret, "regenerateSecret Error");
    }

    private ResponseEntity<ApiSecret> getSecretResponse(Optional<ApiSecret> apiSecret, String error){
        if (apiSecret.isPresent()){
            return ResponseEntity.ok().body(apiSecret.get());
        }
        throw new SecretNotFoundException(error);
    }
}













