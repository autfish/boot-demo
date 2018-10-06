package com.example.eureka.consumer.controller;

import com.example.eureka.consumer.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/consumer")
    public User userConsumer() {
        User user = restTemplate.getForEntity("http://HELLO-SERVICE/user?id=1&delay=false", User.class).getBody();
        return user;
    }
}
