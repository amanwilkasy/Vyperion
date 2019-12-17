package com.vyperion;

import com.vyperion.dto.ApiKey;
import com.vyperion.dto.ApiSecret;
import com.vyperion.dto.DefaultRoles;
import com.vyperion.dto.User;
import com.vyperion.repositories.ApiKeyRepository;
import com.vyperion.repositories.ApiSecretRepository;
import com.vyperion.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class VyperionCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ApiSecretRepository apiSecretRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApiKeyRepository apiKeyRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (false) {
            apiSecretRepository.deleteAll();
            apiKeyRepository.deleteAll();
            userRepository.deleteAll();

            User user = new User();
            user.setFirstName("some");
            user.setLastName("guy");
            user.setEmail("me@email.com");
            user.setPassword(passwordEncoder.encode("asdfasdf"));
            user.setRoles(DefaultRoles.USER_ROLE);

            ApiKey apiKey = new ApiKey();
            apiKey.setKeyValue("value");
            apiKey.setKeyName("name");
            apiKey.setUserId(user);

            ApiSecret apiSecret = new ApiSecret();
            apiSecret.setSecret("mygeneratedsecret");
            apiSecret.setUserId(user);

            List<ApiKey> apiKeys = new ArrayList<>();
            apiKeys.add(apiKey);

            //set user
            user.setApiKeys(apiKeys);
            user.setApiSecret(apiSecret);

            //save repo
            apiSecretRepository.save(apiSecret);
            userRepository.save(user);
            apiKeyRepository.save(apiKey);
        }
    }
}