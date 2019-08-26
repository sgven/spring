package demo.spring.jpacomplex.dao;

import demo.spring.jpacomplex.entity.Coffee;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeDao extends BaseDao<Coffee, Long> {
}
