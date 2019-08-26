package demo.spring.jpacomplex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {
    // 指定策略strategy为IDENTITY方式，Hibernate就不会帮我们创建Sequence序列，而是使用数据库IDENTITY自增主键的方式生成主键
    // 也就是说，没有指定策略的话，Hibernate默认会创建一个Sequence，用序列的方式生成主键
    @Id
    @GeneratedValue
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date updateTime;
}
