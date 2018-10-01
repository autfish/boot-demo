package com.example.basic.mapper;

import com.example.basic.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM t_user WHERE id=#{id}")
    User findOne(long id);

    @Insert("INSERT INTO t_user(name,sex,age) VALUES(#{name},#{sex},#{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertOne(User user);

    @Update("UPDATE t_user SET name=#{name},sex=#{sex},age=#{age} WHERE id=#{id}")
    void updateOne(User user);

    @Select("SELECT * FROM t_user")
    List<User> findAll();

    @Select("SELECT * FROM t_user where sex=#{sex} and age=#{age}")
    User findBySexAndAge(String sex, int age);
}
