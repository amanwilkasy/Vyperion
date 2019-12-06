package com.vyperion.controllers;

import com.vyperion.config.jwt.TokenNotifier;
import com.vyperion.dto.ApiKey;
import com.vyperion.dto.BaseApiKey;
import com.vyperion.dto.client.ClientApiKey;
import com.vyperion.dto.client.ClientUser;
import com.vyperion.exceptions.ApiKeyNotFoundException;
import com.vyperion.exceptions.CrossUserException;
import com.vyperion.services.ApiKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("apikey")
public class ApiKeyController implements TokenListener {

    private final ApiKeyService apiKeyService;
    private ClientUser clientUser;

    public ApiKeyController(ApiKeyService apiKeyService, TokenNotifier jwtFilter) {
        this.apiKeyService = apiKeyService;
        jwtFilter.registerObserver(this);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<ApiKey>> getApiKeysByUserId(@PathVariable String id) {
        assertUserRequest(id);
        Optional<List<ApiKey>> apiKeys = apiKeyService.getApiKeysByUserId(id);
        if (apiKeys.isPresent()) {
            return ResponseEntity.ok().body(apiKeys.get());
        }
        throw new ApiKeyNotFoundException("Id of user is not correct");
    }

    @PostMapping
    public ResponseEntity<String> addApiKey(@Valid @RequestBody BaseApiKey apiKey) {
        assertUserRequest(((ClientApiKey) apiKey).getUserId());
        apiKeyService.addApiKey(apiKeyService.clientApiKeyToApiKey(apiKey));
        return ResponseEntity.ok("Added ApiKey ".concat(apiKey.getKeyName()));
    }

    @PutMapping
    public ResponseEntity<String> updateApiKey(@Valid @RequestBody BaseApiKey apiKey) {
        assertUserRequest(((ClientApiKey) apiKey).getUserId());
        apiKeyService.updateApiKey(apiKeyService.clientApiKeyToApiKey(apiKey));
        return ResponseEntity.ok("Updated ApiKey ".concat(apiKey.getKeyName()));
    }


    @DeleteMapping("{userId}/{apiKeyId}")
    public ResponseEntity<String> deleteApiKey(@PathVariable String userId, @PathVariable String apiKeyId) {
        assertUserRequest(userId);
        apiKeyService.deleteApiKey(userId, apiKeyId);
        return ResponseEntity.ok("Deleted ApiKey");
    }

    private void assertUserRequest(String id){
        if (!this.clientUser.getId().equals(id)){
            log.error("User request is not for himself");
            throw new CrossUserException("User request is not for himself");
        }
    }

    @Override
    public void update(ClientUser clientUser) {
        this.clientUser = clientUser;
    }


}
