package com.vyperion.services;

import com.vyperion.dto.ApiKey;
import com.vyperion.dto.BaseApiKey;
import com.vyperion.dto.User;
import com.vyperion.dto.client.ClientApiKey;
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

    public Optional<List<ApiKey>> getApiKeysByUserId(String id) {
        return Optional.ofNullable(apiKeyRepository.findAllByUserId(id));
    }

    private Optional<ApiKey> getApiKeyById(int id) {
        return apiKeyRepository.findById(id);
    }

    public void addApiKey(ApiKey apiKey) {
        try {
            apiKeyRepository.saveAndFlush(apiKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateApiKey(ApiKey apiKey) {
        Optional<ApiKey> stored = getApiKeyById(apiKey.getId());
        if (stored.isEmpty()) {
            throw new ApiKeyNotFoundException("Could not update Api Key");
        }
        stored.get().setKeyName(apiKey.getKeyName());
        stored.get().setKeyValue(apiKey.getKeyValue());
        try {
            addApiKey(stored.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Optional<ApiKey> apiKeyBelongsToUser(String userId, String apiKeyId) {
        Optional<List<ApiKey>> userApiKeys = getApiKeysByUserId(userId);
        Optional<ApiKey> apiKey = Optional.empty();
        if (userApiKeys.isPresent()) {
            apiKey = userApiKeys.get().stream().filter(key -> key.getId() == Integer.parseInt(apiKeyId))
                    .findFirst().map(Optional::of)
                    .orElse(apiKey);
        }
        return apiKey;
    }


    public void deleteApiKey(String userId, String apiKeyId) {
        Optional<ApiKey> apiKey = apiKeyBelongsToUser(userId, apiKeyId);
        if (apiKey.isEmpty()) {
            throw new ApiKeyNotFoundException("Could not delete id that does not exist");
        }
        try {
            apiKeyRepository.delete(apiKey.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ApiKey clientApiKeyToApiKey(BaseApiKey baseApiKey) {
        Optional<User> user = userService.getUserById(((ClientApiKey) baseApiKey).getUserId());
        if (user.isEmpty()) {
            throw new RuntimeException("User Id not found");
        }
        ApiKey normalizedApiKey = new ApiKey();
        try {
            normalizedApiKey.setId(baseApiKey.getId());
            normalizedApiKey.setKeyName(baseApiKey.getKeyName());
            normalizedApiKey.setKeyValue(baseApiKey.getKeyValue());
            normalizedApiKey.setUser(user.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return normalizedApiKey;
    }

//    public ApiKey getApiKeyByName(String id, String keyName) {
//        return apiKeyRepository.findApiKeyByUserIdAndKeyName(id, keyName);
//    }

}
