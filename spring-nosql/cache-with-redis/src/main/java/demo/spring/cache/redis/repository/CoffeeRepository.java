package demo.spring.cache.redis.repository;

import demo.spring.cache.redis.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
