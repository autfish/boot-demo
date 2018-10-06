package com.example.feign.service;

import com.example.feign.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("HELLO-SERVICE")
public interface UserService {

    @RequestMapping("/user")
    User getUser(@RequestParam("id") int id, @RequestParam("delay") boolean delay);
}
