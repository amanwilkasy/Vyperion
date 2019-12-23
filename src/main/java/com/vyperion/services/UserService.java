package com.vyperion.services;


import com.vyperion.dto.BaseUser;
import com.vyperion.config.DefaultRoles;
import com.vyperion.dto.User;
import com.vyperion.exceptions.UserNotFoundException;
import com.vyperion.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUpUserToUser(BaseUser signupUser) {
        User user = new User();
        user.setFirstName(signupUser.getFirstName());
        user.setLastName(signupUser.getLastName());
        user.setEmail(signupUser.getEmail());
        user.setPassword(passwordEncoder.encode(signupUser.getPassword()));
        user.setRoles(DefaultRoles.USER_ROLE);
        return user;
    }

    User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    User getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(UserNotFoundException::new);
    }

    public void addUser(User user) {
        userRepository.saveAndFlush(user);
    }


}









