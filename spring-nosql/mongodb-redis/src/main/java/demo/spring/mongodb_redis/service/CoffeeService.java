package demo.spring.mongodb_redis.service;

import demo.spring.mongodb_redis.entity.Coffee;

import java.util.List;

public interface CoffeeService {
    Coffee save(Coffee entity);
    Coffee findById(String id);
    Coffee findByName(String name);
    void findAll();
    void deleteAll();
}
