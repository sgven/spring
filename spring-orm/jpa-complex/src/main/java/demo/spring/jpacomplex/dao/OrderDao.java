package demo.spring.jpacomplex.dao;

import demo.spring.jpacomplex.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDao extends BaseDao<Order, Long>{
    List<Order> findByCustomerOrderById(String customer);
    List<Order> findByItems_Name(String name);
}
