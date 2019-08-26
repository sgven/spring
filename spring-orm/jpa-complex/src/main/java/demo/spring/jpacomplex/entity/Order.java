package demo.spring.jpacomplex.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_order")
@Builder
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity implements Serializable {

    private String customer;
    @ManyToMany
    @JoinTable(name = "t_order_coffee")
    private List<Coffee> items;
//    @Column(nullable = false)
//    private Integer state;
    @Enumerated
    @Column(nullable = false)
    private OrderState state;

}
