package com.example.hystrix.service;

import com.example.hystrix.domain.User;
import com.example.hystrix.exception.BadRequestException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getUserFallback", ignoreExceptions = {BadRequestException.class})
    public User getUser() {
        return restTemplate.getForEntity("http://HELLO-SERVICE/user?id=1&delay=true", User.class).getBody();
    }

    public User getUserFallback() {
        return new User(0, "error");
    }

    @HystrixCommand
    public Future<User> getUserAsync() {
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                return restTemplate.getForEntity("http://HELLO-SERVICE/user?id=1&delay=true", User.class).getBody();
            }
        };
    }
}
