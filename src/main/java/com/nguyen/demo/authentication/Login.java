package com.nguyen.demo.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Login.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
