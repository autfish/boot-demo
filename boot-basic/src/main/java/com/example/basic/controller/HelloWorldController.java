package com.example.basic.controller;

import com.example.basic.jpa.UserRepository;
import com.example.basic.mapper.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/say.html")
    @ResponseBody
    public String say() {
        System.out.println(userRepository.findById(1L));
        return "Hello Spring Boot " + cardMapper.selectByPrimaryKey(1).getCode();
    }
}
