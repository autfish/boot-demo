package com.example.basic.service.jpa;

import com.example.basic.condition.JpaCondition;
import com.example.basic.domain.User;
import com.example.basic.jpa.UserRepository;
import com.example.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

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
}
