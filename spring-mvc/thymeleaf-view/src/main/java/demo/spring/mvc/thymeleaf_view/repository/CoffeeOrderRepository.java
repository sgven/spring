package demo.spring.mvc.thymeleaf_view.repository;

import demo.spring.mvc.thymeleaf_view.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
