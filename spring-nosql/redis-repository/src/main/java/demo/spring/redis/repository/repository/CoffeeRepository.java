package demo.spring.redis.repository.repository;

import demo.spring.redis.repository.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
