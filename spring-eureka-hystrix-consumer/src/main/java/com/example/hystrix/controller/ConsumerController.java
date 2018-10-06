package com.example.hystrix.controller;

import com.example.hystrix.domain.User;
import com.example.hystrix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class ConsumerController {

    @Autowired
    UserService userService;

    @RequestMapping("/consumer")
    public User getUser() {
        return userService.getUser();
    }

    @RequestMapping("/async")
    public User getUserAsync() {
        try {
            return userService.getUserAsync().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new User(99, "exception");
    }
}
