package demo.spring.jpasimple.dao;

import demo.spring.jpasimple.entity.Coffee;
import demo.spring.jpasimple.entity.Order;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeDao extends CrudRepository<Coffee, Long> {
}
