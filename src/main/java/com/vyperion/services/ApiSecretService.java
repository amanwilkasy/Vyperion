package com.vyperion.services;

import com.vyperion.dto.ApiSecret;
import com.vyperion.dto.User;
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

    public Optional<ApiSecret> findApiSecretByUserId(String userId) {
        return Optional.ofNullable(apiSecretRepository.findApiSecretByUserId(userId));
    }

    public Optional<ApiSecret> regenerateApiSecret(String userId){
        Optional<ApiSecret> apiSecret = findApiSecretByUserId(userId);
        if (apiSecret.isPresent()){
            apiSecret.get().setSecret(UUID.randomUUID().toString());
            apiSecretRepository.save(apiSecret.get());
            return apiSecret;
        }
        return generateApiSecret(userId);
    }

    public Optional<ApiSecret> generateApiSecret(String userId){
        Optional<ApiSecret> apiSecret = findApiSecretByUserId(userId);
        if (apiSecret.isPresent()){
            return apiSecret;
        }
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()){
            apiSecret = Optional.of(new ApiSecret());
            apiSecret.get().setSecret(UUID.randomUUID().toString());
            apiSecret.get().setUser(user.get());
            apiSecretRepository.save(apiSecret.get());
            return apiSecret;
        }
        throw new RuntimeException("User not found");
    }
}
