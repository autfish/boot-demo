package com.example.basic.mapper;

import com.example.basic.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM t_user WHERE id=#{id}")
    User findOne(long id);
}
