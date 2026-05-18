package com.test.gitradar.utils;

import com.test.gitradar.exceptions.OAuth2AttributeException;
import com.test.gitradar.exceptions.UserNotFoundException;
import com.test.gitradar.models.UserModel;
import com.test.gitradar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PrincipalUtil {

    @Autowired
    private UserRepository userRepository;

    public Long extractGithubId(OAuth2User principal) {
        if (principal == null) {
            throw new UserNotFoundException();
        }

        Object idObj = principal.getAttribute("id");

        if (idObj instanceof Number) {
            return ((Number) idObj).longValue();
        }

        throw new OAuth2AttributeException();
    }

    @Transactional(readOnly = true)
    public UserModel getPrincipalUser(OAuth2User principal) {

        Long githubId = extractGithubId(principal);

        return userRepository.findById(githubId).orElseThrow(UserNotFoundException::new);
    }
}