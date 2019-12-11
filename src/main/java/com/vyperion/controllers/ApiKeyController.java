package com.vyperion.controllers;

import com.vyperion.config.jwt.JWTFilter;
import com.vyperion.dto.ApiKey;
import com.vyperion.dto.BaseApiKey;
import com.vyperion.dto.client.ClientUser;
import com.vyperion.services.ApiKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("apikey")
public class ApiKeyController {
    private final JWTFilter jwtFilter;
    private final ApiKeyService apiKeyService;

    public ApiKeyController(ApiKeyService apiKeyService, JWTFilter jwtFilter) {
        this.apiKeyService = apiKeyService;
        this.jwtFilter = jwtFilter;
    }

    @GetMapping
    public ResponseEntity<List<ApiKey>> getApiKeysByUserId() {
        return ResponseEntity.ok().body(apiKeyService.getApiKeysByUserId(getClientUser().getId()));

    }

    @PostMapping
    public ResponseEntity<String> addApiKey(@Valid @RequestBody BaseApiKey apiKey) {
        apiKeyService.addApiKey(apiKeyService.clientApiKeyToApiKey(apiKey, getClientUser().getId()));
        return ResponseEntity.ok("Added ApiKey ".concat(apiKey.getKeyName()));
    }

    @PutMapping
    public ResponseEntity<String> updateApiKey(@Valid @RequestBody BaseApiKey apiKey) {
        apiKeyService.updateApiKey(apiKeyService.clientApiKeyToApiKey(apiKey, getClientUser().getId()));
        return ResponseEntity.ok("Updated ApiKey ".concat(apiKey.getKeyName()));
    }


    @DeleteMapping("{apiKeyId}")
    public ResponseEntity<String> deleteApiKey(@PathVariable String apiKeyId) {
        apiKeyService.deleteApiKey(getClientUser().getId(), apiKeyId);
        return ResponseEntity.ok("Deleted ApiKey");
    }

    private ClientUser getClientUser() {
        return jwtFilter.getClientUser();
    }
}
