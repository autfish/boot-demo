package com.example.basic.service.jpa;

import com.example.basic.condition.JpaCondition;
import com.example.basic.domain.User;
import com.example.basic.jpa.UserRepository;
import com.example.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

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
        return userRepository.findById(id).get();
    }
}
