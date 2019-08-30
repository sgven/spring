package demo.spring.mvc.simplecontroller.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "t_order")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeOrder extends BaseEntity implements Serializable {
    private String customer;
    @ManyToMany
    @JoinTable(name = "t_order_coffee")
    @OrderBy("id")
    private List<Coffee> items;
    // 当我需要持久化一个枚举类字段的时候，就可以用@Enumerated来标注枚举类型
    @Enumerated
    @Column(nullable = false) // 不能为空
    private OrderState state;
}
