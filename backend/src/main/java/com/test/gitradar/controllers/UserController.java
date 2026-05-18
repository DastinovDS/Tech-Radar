package com.test.gitradar.controllers;

import com.test.gitradar.models.UserModel;
import com.test.gitradar.utils.PrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private PrincipalUtil principalUtil;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        UserModel user = principalUtil.getPrincipalUser(principal);

        return ResponseEntity.ok(Map.of(
                "id", user.getGithubId(),
                "login", user.getLogin(),
                "avatarUrl", user.getAvatarUrl(),
                "hasCompletedOnboarding", user.isHasCompletedOnboarding(),
                "status", user.isHasCompletedOnboarding() ? "READY" : "ONBOARDING_REQUIRED"
        ));
    }
}