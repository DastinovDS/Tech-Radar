package com.test.gitradar.services;

import com.test.gitradar.dto.UserProfileDTO;
import com.test.gitradar.exceptions.UserNotFoundException;
import com.test.gitradar.models.UserModel;
import com.test.gitradar.repositories.UserRepository;
import com.test.gitradar.utils.PrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PrincipalUtil principalUtil;

    @Transactional
    public void deleteAccount(OAuth2User principal){

        UserModel user = principalUtil.getPrincipalUser(principal);

        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfile(OAuth2User principal){

        UserModel user = principalUtil.getPrincipalUser(principal);

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUsername(user.getLogin());
        userProfileDTO.setAvatarUrl(user.getAvatarUrl());
        userProfileDTO.setTrackedForLeaderboard(user.isTrackedForLeaderboard());
        userProfileDTO.setCompletedOnboarding(user.isHasCompletedOnboarding());

        // userProfileDTO.setActivityRate(calculateActivityRateFunc());

        return userProfileDTO;
    }
}
