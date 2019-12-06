package com.vyperion.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.vyperion.dto.client.SignInUser;
import com.vyperion.dto.client.SignUpUser;
import lombok.Data;

import java.util.Set;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SignUpUser.class, name = "signUpUser"),
        @JsonSubTypes.Type(value = SignInUser.class, name = "signInUser")}
)
public abstract class BaseUser {
//    private String email = "some";

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Set<String> roles;

//@AllArgsConstructor
//@NoArgsConstructor
//@JsonDeserialize(as = BaseImpl.class)
//@JsonIgnoreProperties(ignoreUnknown = true)

}
