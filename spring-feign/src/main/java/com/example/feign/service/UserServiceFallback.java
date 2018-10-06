package com.example.feign.service;

import com.example.feign.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallback implements UserService {
    @Override
    public User getUser(int id, boolean delay) {
        return new User(-1, "error-1");
    }
}
