package demo.spring.jpacomplex.dao;

import demo.spring.jpacomplex.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDao extends BaseDao<Order, Long>{
    List<Order> findByCustomerOrderById(String customer);

    /**
     * 注意：Items_Name
     * findByItemsName也能进去查找到Items的name属性，但可能Order也有ItemsName属性呢？那么findByItemsName就会按照ItemsName来查找了
     * 所以为了避免混淆，在Items后面加下划线
     *
     * 属性表达式只能引用被管实体的直接属性
     * “_”用来解决歧义
     *
     * 参考文档：https://docs.spring.io/spring-data/data-commons/docs/1.11.0.RELEASE/reference/html/#repositories.query-methods.query-property-expressions
     * @param name
     * @return
     */
    List<Order> findByItems_Name(String name);
}
