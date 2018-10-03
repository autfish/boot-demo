package com.example.boot.service;

import com.example.boot.domain.User;

import java.util.List;

public interface UserService {

    User findById(long id);

    void save(User user);

    List<User> findAll();
}
