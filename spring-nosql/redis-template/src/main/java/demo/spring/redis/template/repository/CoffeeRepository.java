package demo.spring.redis.template.repository;

import demo.spring.redis.template.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
