package com.example.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("example.starter")
public class DateTimeFormatServiceProperties {

    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
