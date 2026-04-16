package com.test.gitradar.controllers;


import com.test.gitradar.models.UserModel;
import com.test.gitradar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    public String addNewUser(@RequestParam Long githubId, @RequestParam String login, @RequestParam int following) {
        UserModel user = new UserModel();

        user.setGithubId(githubId);
        user.setLogin(login);
        user.setFollowing(following);

        userRepository.save(user);
        return "User saved successfully";
    }
}
