package com.test.gitradar.controllers;

import com.test.gitradar.models.UserModel;
import com.test.gitradar.repositories.UserRepository;
import com.test.gitradar.utils.PrincipalUtil;
import com.test.gitradar.exceptions.UserNotFoundException;
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
public class UserStatusController {

    @Autowired
    private PrincipalUtil principalUtil;

    @GetMapping("/status")
    public ResponseEntity<?> getUserStatus(@AuthenticationPrincipal OAuth2User principal) {

        UserModel user = principalUtil.getPrincipalUser(principal);

        if (!user.isHasCompletedOnboarding()) {
            return ResponseEntity.ok(Map.of(
                    "status", "ONBOARDING_REQUIRED",
                    "login", user.getLogin(),
                    "avatarUrl", user.getAvatarUrl()
            ));
        }

        return ResponseEntity.ok(Map.of(
                "status", "READY",
                "login", user.getLogin()
        ));
    }
}