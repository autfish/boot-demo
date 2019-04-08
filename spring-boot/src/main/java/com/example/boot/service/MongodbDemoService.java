package com.example.boot.service;

import com.example.boot.domain.MongodbDemo;

import java.util.List;

public interface MongodbDemoService {

    void save(MongodbDemo mongodbDemo);

    MongodbDemo findById(Integer id);

    List<MongodbDemo> findAll();
}
