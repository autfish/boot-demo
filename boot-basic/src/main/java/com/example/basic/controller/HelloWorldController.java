package com.example.basic.controller;

import com.example.basic.mapper.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    @Autowired
    private CardMapper cardMapper;

    @RequestMapping("/say.html")
    @ResponseBody
    public String say() {
        return "Hello Spring Boot " + cardMapper.selectByPrimaryKey(1).getCode();
    }
}
