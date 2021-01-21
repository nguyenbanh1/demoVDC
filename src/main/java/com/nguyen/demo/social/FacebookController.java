package com.nguyen.demo.social;

import com.nguyen.demo.authentication.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class FacebookController {

    private final FacebookService facebookService;

    @GetMapping
    public List<String> welcome() {
        List<String> urls = new ArrayList<>();
        urls.add("http://localhost:8080/generateFacebookAuthorizeUrl");
        return urls;
    }

    @GetMapping("/generateFacebookAuthorizeUrl")
    public String generateFacebookAuthorizeUrl() {
        return facebookService.generateFacebookAuthorizeUrl();
    }

    @GetMapping("/facebook")
    public JwtToken generateFacebookAccessToken(@RequestParam("code") String code) {
        return facebookService.generateFacebookAccessToken(code);
    }
}
