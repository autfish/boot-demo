package com.example.basic;

import com.example.basic.domain.User;
import com.example.basic.jpa.UserRepository;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindBySexAndAge() {
        List<User> list = userRepository.findBySexAndAge("m", 0);
        list.forEach(v -> {
            System.out.println(ToStringBuilder.reflectionToString(v));
        });
    }
}
