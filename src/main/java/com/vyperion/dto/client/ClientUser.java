package com.vyperion.dto.client;

import com.vyperion.dto.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientUser {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private Set<String> roles;

    public ClientUser(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
