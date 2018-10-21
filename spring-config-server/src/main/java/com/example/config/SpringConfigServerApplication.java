package com.example.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SpringConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringConfigServerApplication.class, args);

        //http://localhost:7001/demo/default  demo.yml
        //http://localhost:7001/demo/dev  demo-dev.yml
        //http://localhost:7001/demo/dev/some-branch

    }
}
