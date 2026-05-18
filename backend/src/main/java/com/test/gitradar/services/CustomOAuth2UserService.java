package com.test.gitradar.services;

import com.test.gitradar.exceptions.UserNotFoundException;
import com.test.gitradar.models.UserModel;
import com.test.gitradar.repositories.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuth2User oAuth2User = super.loadUser(userRequest);

        Object idObj = oAuth2User.getAttributes().get("id");

        Long githubId;

        if(idObj instanceof Integer){
            githubId = ((Integer) idObj).longValue();
        }
        else if(idObj instanceof Long){
            githubId = (Long) idObj;
        }
        else{
            throw new OAuth2AuthenticationException("Invalid ID type from GitHub");
        }

        String name = oAuth2User.getAttribute("login");
        String avatarUrl = oAuth2User.getAttribute("avatar_url");


        if(!userRepository.existsById(githubId)){
            UserModel newUser = new UserModel();
            newUser.setAccessToken(accessToken);
            newUser.setGithubId(githubId);
            newUser.setLogin(name);
            newUser.setAvatarUrl(avatarUrl);
            newUser.setLastSync(LocalDateTime.now());
            newUser.setHasCompletedOnboarding(false);

            userRepository.save(newUser);
        }
        else{
            UserModel user = userRepository.findById(githubId).orElseThrow(UserNotFoundException::new);
            user.setLogin(name);
            user.setAccessToken(accessToken);
            user.setLastSync(LocalDateTime.now());

            userRepository.save(user);
        }

        return oAuth2User;
    }
}
