package demo.spring.jpasimple.dao;

import demo.spring.jpasimple.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderDao extends CrudRepository<Order, Long>{
}
