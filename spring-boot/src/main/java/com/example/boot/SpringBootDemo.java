package com.example.boot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDemo {

    static Logger logger = LogManager.getLogger(SpringBootDemo.class);

    public static void main(String[] args) {

        SpringApplication.run(SpringBootDemo.class, args);
        logger.info("Started");
    }
}
