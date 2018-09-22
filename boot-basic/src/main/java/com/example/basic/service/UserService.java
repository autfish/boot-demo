package com.example.basic.service;

import com.example.basic.domain.User;

import java.util.List;

public interface UserService {

    User findById(long id);

    void save(User user);

    List<User> findAll();
}
