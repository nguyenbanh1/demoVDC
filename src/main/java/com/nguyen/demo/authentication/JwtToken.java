package com.nguyen.demo.authentication;

import lombok.Data;

@Data
public class JwtToken {

    private String token;

    /**
     * Constructor for {@link JwtToken}.
     *
     * @param token the given jwt token
     */
    public JwtToken(String token) {
        this.token = token;
    }

}
