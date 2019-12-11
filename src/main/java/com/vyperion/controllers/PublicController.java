package com.vyperion.controllers;

import com.vyperion.config.jwt.Token;
import com.vyperion.dto.BaseUser;
import com.vyperion.dto.client.ClientUser;
import com.vyperion.dto.client.ResponseWrapper;
import com.vyperion.services.AuthenticationService;
import com.vyperion.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("public")
public class PublicController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public PublicController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        return ResponseEntity.ok("Signed Out User");
    }

    @PostMapping("signin")
    public ResponseEntity<Token> signIn(@Valid @RequestBody BaseUser signInUser) {
        return ResponseEntity.ok().body(authenticationService.signIn(signInUser));
    }

    @PostMapping("signup")
    public ResponseEntity<String> signUpUser(@Valid @RequestBody BaseUser signUpUser) {
        userService.addUser(userService.signUpUserToUser(signUpUser));
        return ResponseEntity.ok("Signed Up ".concat(signUpUser.getFirstName()));

    }


    @GetMapping("test")
    public ResponseEntity<ResponseWrapper> some() {
        return ResponseEntity.ok().body(new ResponseWrapper(new ClientUser()));
    }

}

