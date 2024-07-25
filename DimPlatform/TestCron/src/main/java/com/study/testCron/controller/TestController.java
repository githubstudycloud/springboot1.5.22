package com.study.testCron.controller;


import com.study.testCron.bean.User;
import com.study.testCron.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;


    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return testService.getAllUsers();
    }

}