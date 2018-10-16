package com.example.boot.controller;

import com.alibaba.fastjson.JSON;
import com.example.boot.domain.User;
import com.example.boot.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user/index";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(@RequestParam("id") int id, Model model) {
        User user = userService.findById(id);
        if(user == null)
            user = new User();
        model.addAttribute("user", user);

        Map<String, String> sexs = new HashMap<>();
        sexs.put("m", "男");
        sexs.put("f", "女");
        model.addAttribute("sexs", sexs);

        return "user/user";
    }

    @RequestMapping(value = "/rest", method = RequestMethod.GET)
    @ResponseBody
    public String getJson(@RequestParam("id") int id) {
        User user = userService.findById(id);
        if(user == null)
            user = new User();
        return JSON.toJSONString(user);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(User user) {
        userService.save(user);
        return "ok";
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String page(Model model, Integer pageNum, Integer pageSize) {

        if(pageNum == null || pageNum <= 0)
            pageNum = 1;
        if(pageSize == null || pageSize <= 0)
            pageSize = 5;

        PageInfo<User> users = userService.findAll(pageNum, pageSize);
        System.out.println("users.getPages()=" + users.getPages());
        System.out.println("users.getSize()=" + users.getSize());
        System.out.println("users.getPageSize()=" + users.getPageSize());
        System.out.println("users.getPageNum()=" + users.getPageNum());
        users.getList().forEach(v -> {
            System.out.println(JSON.toJSONString(v));
        });
        model.addAttribute("pageInfo", users);
        return "user/page";
    }
}
