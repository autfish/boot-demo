package com.example.boot.controller;

import com.alibaba.fastjson.JSON;
import com.example.boot.config.DataSourceContextHolder;
import com.example.boot.domain.User;
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

        DataSourceContextHolder.setDataSourceType("data-source1");
        User user = userMapper.findOne(4);
        System.out.println(JSON.toJSONString(user));
    }
}
