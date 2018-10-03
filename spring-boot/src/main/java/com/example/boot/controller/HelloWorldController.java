package com.example.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class HelloWorldController {

    Logger logger = LogManager.getLogger(HelloWorldController.class);

    @RequestMapping("/hi")
    @ResponseBody
    public String say() {
        logger.info("Hello Spring Boot");
        return "Hello Spring Boot";
    }

    @RequestMapping("/zero")
    public String zero(Model model) {
        int x = 0;
        int y = 100 / x;
        model.addAttribute("hello", y);
        return "index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("hello", "Hello world");
        return "index";
    }
}
