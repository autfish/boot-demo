package com.example.boot.controller;

import com.example.boot.config.DataSourceContextHolder;
import com.example.boot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/test")
    @ResponseBody
    public void test() {

        DataSourceContextHolder.setDataSourceType("data-source2");
    }
}
