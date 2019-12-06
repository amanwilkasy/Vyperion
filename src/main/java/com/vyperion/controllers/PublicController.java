package com.vyperion.controllers;

import com.vyperion.dto.BaseUser;
import com.vyperion.dto.client.SignInUser;
import com.vyperion.dto.client.SignUpUser;
import com.vyperion.services.AuthenticationService;
import com.vyperion.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("signin")
    public String signIn(@Valid @RequestBody BaseUser signInUser) {
        return authenticationService.signIn((SignInUser) signInUser);
    }

    @PostMapping("signup")
    public void saveUser(@Valid @RequestBody BaseUser signUpUser) {
        userService.addUser(userService.signUpUserToUser((SignUpUser) signUpUser));
    }

//    @PostMapping("test")
//    public String test(@Valid @RequestBody BaseUser signInUser) throws IOException {
//        System.out.println(signInUser);
////        BaseUser impl1 = new SignUpUser();
////        BaseUser impl2 = new SignInUser();
////        BaseApiKey some1 = new ApiKey();
////        BaseApiKey some2 = new ClientApiKey();
////        new ObjectMapper().writeValue(new File("src/main/java/com/example/demo/dto/client/SignUpUser.txt"), impl1);
////        new ObjectMapper().writeValue(new File("src/main/java/com/example/demo/dto/client/SignInUser.txt"), impl2);
////        new ObjectMapper().writeValue(new File("src/main/java/com/example/demo/dto/client/ApiKey.txt"), some1);
////        new ObjectMapper().writeValue(new File("src/main/java/com/example/demo/dto/client/ClientApiKey.txt"), some2);
//        return "hit";
//    }

}

