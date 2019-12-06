package com.vyperion.services;


import com.vyperion.dto.DefaultRoles;
import com.vyperion.dto.User;
import com.vyperion.dto.client.SignUpUser;
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

    public User signUpUserToUser(SignUpUser signupUser) {
        User user = new User();
        user.setFirstName(signupUser.getFirstName());
        user.setLastName(signupUser.getLastName());
        user.setEmail(signupUser.getEmail());
        user.setPassword(passwordEncoder.encode(signupUser.getPassword()));
        user.setRoles(DefaultRoles.USER_ROLE);
        return user;
    }

    Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    public void addUser(User user){
        userRepository.saveAndFlush(encryptPassword(user));
    }

    private User encryptPassword(User user){
         user.setPassword(passwordEncoder.encode(user.getPassword()));
         return user;
    }

}









