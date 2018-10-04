package com.example.hystrix.controller;

import com.example.hystrix.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    HelloService helloService;

    @RequestMapping("/consumer")
    public String helloConsumer() {
        return helloService.helloService();
    }
}
