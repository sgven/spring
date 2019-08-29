package demo.spring.mongodb_redis.entity;

import lombok.*;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coffee extends BaseEntity implements Serializable {
//    @Id
//    private String id;
    private String name;
    private Money price;
}
