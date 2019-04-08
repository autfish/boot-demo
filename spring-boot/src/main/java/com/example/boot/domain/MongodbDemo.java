package com.example.boot.domain;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class MongodbDemo {

    @Id
    private int id;
    private String name;
    private Date updateTime;

    public MongodbDemo() {
    }

    public MongodbDemo(int id, String name, Date updateTime) {
        this.id = id;
        this.name = name;
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
