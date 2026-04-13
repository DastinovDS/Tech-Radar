package com.test.gitradar.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    private List<Integer> list = new ArrayList<>();

    private void fill_list(){
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @GetMapping("/")
    public List<Integer> test(){
        fill_list();
        return list;
    }
}
