package com.example.basic.service.mybatis;

import com.example.basic.condition.MybatisCondition;
import com.example.basic.domain.User;
import com.example.basic.mapper.UserMapper;
import com.example.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

@Service
@Conditional(MybatisCondition.class)
public class UserServiceMybatisImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public UserServiceMybatisImpl() {
        System.out.println("create UserServiceMybatisImpl");
    }

    @Override
    public User findById(long id) {
        System.out.println("mybatis");
        return userMapper.findOne(id);
    }

    @Override
    public void save(User user) {
        User other = userMapper.findOne(user.getId());
        if(other == null)
            userMapper.insertOne(user);
        else
            userMapper.updateOne(user);
    }
}
