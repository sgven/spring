package demo.spring.jpasimple.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class Coffee implements Serializable{

    // 指定策略strategy为IDENTITY方式，Hibernate就不会帮我们创建Sequence序列，而是使用数据库IDENTITY自增主键的方式生成主键
    // 也就是说，没有指定策略的话，Hibernate默认会创建一个Sequence，用序列的方式生成主键
    @Id
//    @GeneratedValue
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column
    // PersistentMoneyAmount: 金额存的是decimal
    // PersistentMoneyMinorAmount: 金额存的是bigint
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
//    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money price;
    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date updateTime;

}
