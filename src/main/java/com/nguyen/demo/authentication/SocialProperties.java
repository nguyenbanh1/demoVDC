package com.nguyen.demo.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("social")
@Configuration
@Getter
@Setter
public class SocialProperties {

    private Facebook facebook;

    @Getter
    @Setter
    public static class Facebook {
        private String appId;
        private String appSecret;
    }
}
