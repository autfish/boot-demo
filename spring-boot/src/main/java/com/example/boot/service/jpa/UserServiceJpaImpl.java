package com.example.boot.service.jpa;

import com.example.boot.condition.JpaCondition;
import com.example.boot.domain.User;
import com.example.boot.jpa.UserRepository;
import com.example.boot.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Conditional(JpaCondition.class)
public class UserServiceJpaImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceJpaImpl() {
        System.out.println("create UserServiceJpaImpl");
    }

    @Override
    public User findById(long id) {
        Optional<User> o = userRepository.findById(id);
        if(o.isPresent())
            return o.get();
        return null;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public PageInfo<User> findAll(int pageNum, int pageSize) {
        return null;
    }
}
