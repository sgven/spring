package demo.spring.mvc.complex_controller.repository;

import demo.spring.mvc.complex_controller.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
