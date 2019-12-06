package com.vyperion.dto.client;

import com.vyperion.dto.BaseUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SignInUser extends BaseUser {

    @NotNull
    @Size(min = 4, max = 64)
    private String email;

    @NotNull
    @Size(min = 4, max = 64)
    private String password;
}
