package com.example.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/redis/get")
    public String get() {
        stringRedisTemplate.opsForValue().set("redis.key", "Spring Boot Redis");
        return "redis.key=" + stringRedisTemplate.opsForValue().get("redis.key");
    }
}
