package com.example.boot.service.mybatis;

import com.example.boot.condition.MybatisCondition;
import com.example.boot.domain.User;
import com.example.boot.mapper.UserMapper;
import com.example.boot.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public PageInfo<User> findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.findAll();
        return new PageInfo<>(userList);
    }
}
