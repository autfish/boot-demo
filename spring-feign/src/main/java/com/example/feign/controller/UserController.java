package com.example.feign.controller;

import com.example.feign.domain.User;
import com.example.feign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/consumer")
    public User getUser() {
        return userService.getUser(1, false);
    }
}
