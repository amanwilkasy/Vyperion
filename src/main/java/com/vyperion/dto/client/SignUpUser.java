package com.vyperion.dto.client;

import com.vyperion.dto.BaseUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SignUpUser extends BaseUser {

    @NotNull
    @Size(min = 1, max = 64)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 64)
    private String lastName;

    @NotNull
    @Size(min = 12, max = 64)
    private String email;

    @NotNull
    @Size(min = 8, max = 64)
    private String password;
}










