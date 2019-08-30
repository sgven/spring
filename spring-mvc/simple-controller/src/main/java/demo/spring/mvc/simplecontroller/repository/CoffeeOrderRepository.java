package demo.spring.mvc.simplecontroller.repository;

import demo.spring.mvc.simplecontroller.entity.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
