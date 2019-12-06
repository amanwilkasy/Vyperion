package com.vyperion.services;

import com.vyperion.config.jwt.TokenProvider;
import com.vyperion.dto.client.SignInUser;
import com.vyperion.dto.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserService userService, TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public String signIn(SignInUser signInUser) {
        try {
            Optional<User> user = userService.getUserByEmail(signInUser.getEmail());
            if (user.isPresent()) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInUser.getEmail(), signInUser.getPassword()));
                return tokenProvider.createToken(user.get());
            }
            throw new UsernameNotFoundException("User Email '" + signInUser.getEmail() + "' not found");

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}

