package com.example.basic.service;

import com.example.basic.domain.User;

public interface UserService {

    User findById(long id);

    void save(User user);
}
