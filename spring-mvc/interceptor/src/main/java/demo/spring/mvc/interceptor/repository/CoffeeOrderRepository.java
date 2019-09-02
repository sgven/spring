package demo.spring.mvc.interceptor.repository;

import demo.spring.mvc.interceptor.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
