package com.nguyen.demo.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Authentication Service.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;


    /**
     * Login with the given information.
     *
     * @param login the given login information.
     * @return the instance of {@link JwtToken}.
     */
    public JwtToken login(Login login) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return tokenProvider.createToken(authentication);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid username or password.");
        }
    }
}