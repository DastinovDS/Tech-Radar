package com.test.gitradar.controllers;

import com.test.gitradar.models.RepositoryModel;

import com.test.gitradar.models.UrlModel;
import com.test.gitradar.services.HomepageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/public")
public class HomepageController {

    @Autowired
    HomepageService homepageService;

    @GetMapping("/")
    public String getHomepage() {
        return "Welcome to the public homepage!";
    }

    @GetMapping("/gatherinfo")
    public String gatherInfo(@RequestParam String url) {
        UrlModel urlModel = new UrlModel();
        urlModel.setUrl(url);
        RepositoryModel repositoryModel = homepageService.getRepo(urlModel);
        if (repositoryModel != null) {
            return "Success!";
        }
        else  {
            return "Error!";
        }
    }
}
