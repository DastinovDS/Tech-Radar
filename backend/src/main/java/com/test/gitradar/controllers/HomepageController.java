package com.test.gitradar.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/public")
public class HomepageController {

    @GetMapping("/")
    public String getHomepage() {
        return "Welcome to the public homepage!";
    }
}
