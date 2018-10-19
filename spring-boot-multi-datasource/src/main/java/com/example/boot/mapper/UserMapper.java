package com.example.boot.mapper;

import com.example.boot.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    User findOne(long id);

    List<User> findAll(@Param("table") String table, Map<String, Object> params, @Param("limit") int limit);
}
