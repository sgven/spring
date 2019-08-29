package demo.spring.mongodb_redis.repository;

import demo.spring.mongodb_redis.entity.Coffee;
import demo.spring.mongodb_redis.entity.CoffeeCache;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CoffeeCacheRepository extends CrudRepository<CoffeeCache, String> {
    Optional<CoffeeCache> findOneByName(String name);
}
