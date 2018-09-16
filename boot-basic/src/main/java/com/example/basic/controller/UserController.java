package com.example.basic.controller;

import com.example.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/user/get")
    @ResponseBody
    public String get() {
        return userService.findById(1).getName();
    }
}
