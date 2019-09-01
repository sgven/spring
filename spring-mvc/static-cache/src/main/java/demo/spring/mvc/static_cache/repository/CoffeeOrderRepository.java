package demo.spring.mvc.static_cache.repository;

import demo.spring.mvc.static_cache.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
