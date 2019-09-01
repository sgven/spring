package demo.spring.mvc.jsonview.repository;

import demo.spring.mvc.jsonview.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
