package com.example.boot.mapper;

import com.example.boot.domain.User;

public interface UserMapper {

    User findOne(long id);
}
