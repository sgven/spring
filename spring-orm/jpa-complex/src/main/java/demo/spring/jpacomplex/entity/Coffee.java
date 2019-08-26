package demo.spring.jpacomplex.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.money.Money;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_coffee")
@Builder
@Data
@ToString(callSuper = true) // 继承父类的话，toString要打印父类属性需要设置callSuper = true
@NoArgsConstructor
@AllArgsConstructor
public class Coffee extends BaseEntity implements Serializable{

    private String name;
    @Column
    // PersistentMoneyAmount: 金额存的是decimal
    // PersistentMoneyMinorAmount: 金额存的是bigint
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
//    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money price;

}
