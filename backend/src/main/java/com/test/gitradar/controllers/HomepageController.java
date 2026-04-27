package com.test.gitradar.controllers;

import com.test.gitradar.models.RepositoryModel;

import com.test.gitradar.models.UrlModel;
import com.test.gitradar.services.RepoApiRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/public")
public class HomepageController {

    @Autowired
    RepoApiRequestService homepageService;

    @GetMapping("/")
    public String getHomepage() {
        return "Welcome to the public homepage!";
    }

    @GetMapping("/gatherinfo")
    public String gatherInfo() {
        return "WOOOOW";
    }
}
