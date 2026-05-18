package com.test.gitradar.controllers;

import com.test.gitradar.models.UserModel;
import com.test.gitradar.services.RepositoryService;
import com.test.gitradar.repositories.UserRepository;
import com.test.gitradar.utils.PrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/onboarding")
public class OnboardingController {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PrincipalUtil principalUtil;

    @PostMapping("/init")
    public ResponseEntity<String> initRepositories(@AuthenticationPrincipal OAuth2User principal) {

        Long githubId = principalUtil.extractGithubId(principal);

        repositoryService.addRepositories(githubId);

        return ResponseEntity.ok("Repositories were successfully synced. Choose repositories to track.");
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmSelection(@AuthenticationPrincipal OAuth2User principal,
                                                   @RequestBody List<Long> repoIds) {

        UserModel user = principalUtil.getPrincipalUser(principal);

        repositoryService.activateRepositories(user.getGithubId(), repoIds);

        user.setHasCompletedOnboarding(true);

        userRepository.save(user);

        return ResponseEntity.ok("Tracking successfully set for " + repoIds.size() + " repositories.");
    }
}