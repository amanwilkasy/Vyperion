package com.vyperion.services;

import com.vyperion.dto.ApiKey;
import com.vyperion.dto.BaseApiKey;
import com.vyperion.dto.User;
import com.vyperion.exceptions.ApiKeyNotFoundException;
import com.vyperion.repositories.ApiKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final UserService userService;

    public ApiKeyService(ApiKeyRepository apiKeyRepository, UserService userService) {
        this.apiKeyRepository = apiKeyRepository;
        this.userService = userService;
    }

    public List<ApiKey> getApiKeysByUserId(String id) {
        return Optional.ofNullable(apiKeyRepository.findAllByUserId(id)).orElseThrow(ApiKeyNotFoundException::new);
    }

    private ApiKey getApiKeyById(int id) {
        return apiKeyRepository.findById(id).orElseThrow(ApiKeyNotFoundException::new);
    }

    public void addApiKey(ApiKey apiKey) {
        apiKeyRepository.saveAndFlush(apiKey);
    }

    public void updateApiKey(ApiKey apiKey) {
        ApiKey stored = getApiKeyById(apiKey.getId());
        stored.setKeyName(apiKey.getKeyName());
        stored.setKeyValue(apiKey.getKeyValue());
        addApiKey(stored);
    }

    private Optional<ApiKey> apiKeyBelongsToUser(String userId, String apiKeyId) {
        List<ApiKey> userApiKeys = getApiKeysByUserId(userId);
        return userApiKeys.stream().filter(key -> key.getId() == Integer.parseInt(apiKeyId))
                .findFirst().map(Optional::of).orElseThrow(ApiKeyNotFoundException::new);
    }


    public void deleteApiKey(String userId, String apiKeyId) {
        Optional<ApiKey> apiKey = apiKeyBelongsToUser(userId, apiKeyId);
        apiKeyRepository.delete(apiKey.orElseThrow(ApiKeyNotFoundException::new));
    }

    public ApiKey clientApiKeyToApiKey(BaseApiKey baseApiKey, String userId) {
        User user = userService.getUserById(userId);
        return new ApiKey(baseApiKey.getId(), baseApiKey.getKeyName(),
                baseApiKey.getKeyValue(), user);
    }

}
