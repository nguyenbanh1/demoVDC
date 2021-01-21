package com.nguyen.demo.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterReq {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6, max = 60)
    private String password;
}
