package com.test.gitradar.controllers;

import com.test.gitradar.dto.UserProfileDTO;
import com.test.gitradar.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserProfileDTO> getProfile(@AuthenticationPrincipal OAuth2User principal) {
        UserProfileDTO profile = userService.getUserProfile(principal);
        return ResponseEntity.ok(profile);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal OAuth2User principal,
            HttpServletRequest request) throws ServletException {

        userService.deleteAccount(principal);

        request.logout();

        return ResponseEntity.ok().build();
    }
}