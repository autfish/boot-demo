package com.example.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootBasicApplication {

    /**
     * java -jar xxx.jar --spring.profiles.active=prod
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(BootBasicApplication.class, args);
    }
}
