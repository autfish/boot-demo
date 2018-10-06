package com.example.eureka.client.controller;

import com.example.eureka.client.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/user")
    public User getUser(@RequestParam int id, @RequestParam boolean delay) {
        if(delay) {
            //模拟触发Hystrix
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new User(id, "name" + id);
    }
}
