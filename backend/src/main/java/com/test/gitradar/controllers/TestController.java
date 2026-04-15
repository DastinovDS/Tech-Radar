package com.test.gitradar.controllers;


import com.test.gitradar.models.UserModel;
import com.test.gitradar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/save")
    public String testSave() {
        UserModel user = new UserModel();

        user.setGithubId(12345L);
        user.setLogin("test_login");
        user.setFollowing(1);

        userRepository.save(user);
        return "User saved successfully";
    }
}
