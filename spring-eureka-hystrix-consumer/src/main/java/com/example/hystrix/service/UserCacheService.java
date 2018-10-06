package com.example.hystrix.service;

import com.example.hystrix.domain.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserCacheService {

    @Autowired
    RestTemplate restTemplate;

    @CacheResult
    @HystrixCommand
    public User getUser(@CacheKey("id") Long id) {
        return restTemplate.getForEntity("http://HELLO-SERVICE/user?id={1}&delay=true", User.class, id).getBody();
    }

    @CacheRemove(commandKey = "getUser")
    @HystrixCommand
    public void update(@CacheKey("id") User user) {
        restTemplate.postForObject("http://HELLO-SERVICE/user?id={1}&delay=true", user, User.class);
    }
}
