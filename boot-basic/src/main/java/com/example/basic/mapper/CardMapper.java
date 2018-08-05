package com.example.basic.mapper;

import com.example.basic.domain.Card;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CardMapper {

    @Select("SELECT * FROM t_card WHERE id=#{id}")
    Card selectByPrimaryKey(Integer id);
}
