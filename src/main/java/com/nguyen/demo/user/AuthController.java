package com.nguyen.demo.user;

import com.nguyen.demo.authentication.AuthService;
import com.nguyen.demo.authentication.JwtToken;
import com.nguyen.demo.authentication.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller to login users.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    /**
     * Login to the account.
     *
     * @param login the given credential
     * @return an instance of {@link JwtToken}
     */
    @PostMapping("/login")
    public JwtToken login(@Valid @RequestBody Login login) {
        return authService.login(login);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterReq registerReq) {
        userService.create(registerReq);
    }

}
