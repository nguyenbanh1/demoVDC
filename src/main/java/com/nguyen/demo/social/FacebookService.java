package com.nguyen.demo.social;

import com.nguyen.demo.authentication.JwtToken;
import com.nguyen.demo.authentication.SocialProperties;
import com.nguyen.demo.authentication.TokenProvider;
import com.nguyen.demo.configuration.ApplicationProperties;
import com.nguyen.demo.core.entity.RolesConstants;
import com.nguyen.demo.user.UserDto;
import com.nguyen.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Service
@RequiredArgsConstructor
public class FacebookService {

    private final SocialProperties socialProperties;

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final ApplicationProperties applicationProperties;

    private FacebookConnectionFactory createConnection() {
        return new FacebookConnectionFactory(
                socialProperties.getFacebook().getAppId(),
                socialProperties.getFacebook().getAppSecret()
        );
    }

    public String generateFacebookAuthorizeUrl() {
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri(applicationProperties.getDomain() + "/facebook");
        params.setScope("email");
        return createConnection().getOAuthOperations().buildAuthenticateUrl(params);
    }

    public JwtToken generateFacebookAccessToken(String code) {
        String accessToken = createConnection().getOAuthOperations()
                .exchangeForAccess(code, applicationProperties.getDomain() + "/facebook", null)
                .getAccessToken();
        UserData userData = getUserData(accessToken);
        String rawPassword = randomAlphabetic(8);
        UserDto userDto = userService.createIfNot(userData.getEmail(), rawPassword, RolesConstants.CUSTOMER);
        return tokenProvider.createToken(userDto.getEmail(), userDto.getRoles());
    }

    public UserData getUserData(String accessToken) {
        Facebook facebook = new FacebookTemplate(accessToken);
        String[] fields = {"id", "first_name", "name", "email", "birthday", "gender", "age_range", "hometown"};
        return facebook.fetchObject("me", UserData.class, fields);
    }
}
