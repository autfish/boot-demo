package com.example.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(DateTimeFormatService.class)
@EnableConfigurationProperties(DateTimeFormatServiceProperties.class)
public class StarterAutoConfigure {

    @Autowired
    private DateTimeFormatServiceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "example.starter", value = "enabled", havingValue = "true")
    DateTimeFormatService starterService (){
        return new DateTimeFormatService(properties.getFormat());
    }
}
