package com.vyperion.services;

import com.vyperion.config.jwt.Token;
import com.vyperion.config.jwt.TokenProvider;
import com.vyperion.dto.BaseUser;
import com.vyperion.dto.User;
import com.vyperion.exceptions.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

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

    public Token signIn(BaseUser signInUser) {
        try {
            User user = userService.getUserByEmail(signInUser.getEmail());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInUser.getEmail(), signInUser.getPassword()));
            return new Token(tokenProvider.createToken(user));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }
}

