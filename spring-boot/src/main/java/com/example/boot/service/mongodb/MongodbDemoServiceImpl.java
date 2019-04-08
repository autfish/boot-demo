package com.example.boot.service.mongodb;

import com.example.boot.domain.MongodbDemo;
import com.example.boot.service.MongodbDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongodbDemoServiceImpl implements MongodbDemoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(MongodbDemo mongodbDemo) {
        mongoTemplate.save(mongodbDemo);
    }

    @Override
    public MongodbDemo findById(Integer id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, MongodbDemo.class);
    }

    @Override
    public List<MongodbDemo> findAll() {
        return mongoTemplate.findAll(MongodbDemo.class);
    }
}
