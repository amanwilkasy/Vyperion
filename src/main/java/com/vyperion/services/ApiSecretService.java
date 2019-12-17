package com.vyperion.services;

import com.vyperion.dto.ApiSecret;
import com.vyperion.dto.User;
import com.vyperion.exceptions.SecretNotFoundException;
import com.vyperion.repositories.ApiSecretRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class ApiSecretService {

    private final ApiSecretRepository apiSecretRepository;
    private final UserService userService;

    public ApiSecretService(ApiSecretRepository apiSecretRepository, UserService userService) {
        this.apiSecretRepository = apiSecretRepository;
        this.userService = userService;
    }

    public ApiSecret findApiSecretByUserId(String userId) {
        return Optional.ofNullable(apiSecretRepository.findApiSecretByUserId(userId)).orElseThrow(SecretNotFoundException::new);
    }

    public ApiSecret regenerateApiSecret(String userId) {
        try {
            ApiSecret apiSecret = findApiSecretByUserId(userId);
            apiSecret.setSecret(UUID.randomUUID().toString());
            return apiSecretRepository.save(apiSecret);
        } catch (SecretNotFoundException e) {
            return generateApiSecret(userId);
        }
    }

    public ApiSecret generateApiSecret(String userId) {
        try {
            return findApiSecretByUserId(userId);
        } catch (SecretNotFoundException e) {
            User user = userService.getUserById(userId);
            ApiSecret apiSecret = new ApiSecret();
            apiSecret.setSecret(UUID.randomUUID().toString());
            apiSecret.setUserId(user);
            return apiSecretRepository.save(apiSecret);
        }
    }
}






