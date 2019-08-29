package demo.spring.mongodb_redis.repository;

import demo.spring.mongodb_redis.entity.Coffee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CoffeeRepository extends MongoRepository<Coffee, String> {
    Coffee findByName(String name);
}
