package com.example.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@SpringCloudApplication=@SpringBootApplication+@EnableDiscoveryClient+@EnableCircuitBreaker
@SpringCloudApplication
@EnableHystrixDashboard
public class SpringEurekaHystrixConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEurekaHystrixConsumerApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
