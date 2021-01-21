package com.nguyen.demo.authentication;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Security properties.
 */
@Configuration
@ConfigurationProperties("security")
@Getter
@Setter
public class SecurityProperties {

    private Authentication authentication;

    /**
     * Authentication.
     */
    @Data
    public static class Authentication {

        private Jwt jwt = new Jwt();

        /**
         * Jwt.
         */
        @Data
        public static class Jwt {

            private String secret;

            private String base64Secret;

            private long tokenValidityInSeconds;

            private long tokenValidityInSecondsForRememberMe;

        }

    }
}
