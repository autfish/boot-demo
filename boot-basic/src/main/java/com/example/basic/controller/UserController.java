package com.example.basic.controller;

import com.example.basic.domain.User;
import com.example.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index() {
        return "user/index";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(@RequestParam("id") int id, Model model) {
        User user = userService.findById(id);
        if(user == null)
            user = new User();
        model.addAttribute("user", user);
        return "user/user";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String get(User user) {
        System.out.println(user.getName());
        userService.save(user);
        return "ok";
    }
}
