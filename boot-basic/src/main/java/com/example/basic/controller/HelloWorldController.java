package com.example.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class HelloWorldController {

    Logger logger = LogManager.getLogger(HelloWorldController.class);

    @RequestMapping("/say.html")
    @ResponseBody
    public String say() {
        logger.info("Hello Spring Boot");
        return "Hello Spring Boot";
    }
}
