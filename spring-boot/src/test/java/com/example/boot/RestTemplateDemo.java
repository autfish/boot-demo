package com.example.boot;

import com.alibaba.fastjson.JSON;
import com.example.boot.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateDemo {

    @Test
    public void getForEntity() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> resp = restTemplate.getForEntity("http://localhost:8080/basic/user/rest?id={1}", User.class, 1);
        User user = resp.getBody();
        System.out.println(JSON.toJSONString(user));
    }
}
