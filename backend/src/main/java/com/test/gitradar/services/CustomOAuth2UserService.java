package com.test.gitradar.services;

import com.test.gitradar.models.UserModel;
import com.test.gitradar.repositories.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


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
        String email = oAuth2User.getAttribute("email");
        if(email == null) email = "hidden@email.com";

        if(!userRepository.existsById(githubId)){
            UserModel newUser = new UserModel();
            newUser.setAccessToken(accessToken);
            newUser.setGithubId(githubId);
            newUser.setLogin(name);
            newUser.setEmail(email);

            userRepository.save(newUser);
        }
        return oAuth2User;
    }
}
