package com.test.gitradar.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.core.user.OAuth2User;


@RestController
public class TestController {

    @GetMapping("/me")
    public String getMe(@AuthenticationPrincipal OAuth2User user) {
        return "Hi, " + user.getAttribute("login") + "! Now you are in our system!";
    }
}
