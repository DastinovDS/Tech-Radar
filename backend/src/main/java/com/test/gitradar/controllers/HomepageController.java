package com.test.gitradar.controllers;

import com.test.gitradar.models.RepositoryModel;

import com.test.gitradar.models.UrlModel;
import com.test.gitradar.services.ApiRequestService;
import com.test.gitradar.services.UrlApiBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/public")
public class HomepageController {

    @Autowired
    ApiRequestService repoApiRequestService;

    @Autowired
    UrlApiBuilderService urlApiBuilderService;

    @GetMapping("/")
    public String getHomepage() {
        return "Welcome to the public homepage!";
    }

    @PostMapping("/gatherinfo")
    public void gatherInfo(@RequestBody UrlModel publicUrl) {

        String apiString = urlApiBuilderService.buildPublicUrl(publicUrl);
    }
}
