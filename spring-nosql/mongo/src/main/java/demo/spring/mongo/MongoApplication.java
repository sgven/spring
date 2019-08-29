package demo.spring.mongo;

import com.mongodb.client.result.UpdateResult;
import demo.spring.mongo.converter.MoneyReadConverter;
import demo.spring.mongo.entity.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@SpringBootApplication
@Slf4j
public class MongoApplication implements ApplicationRunner {
    @Autowired
    private MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class, args);
    }

    /**
     * 为了去使用MoneyReadConverter，在上下文中定义了一个MongoCustomConversions的bean
     * spring-boot-starter-data-mongodb是怎样做配置的，为什么要定义MongoCustomConversions这样一个bean呢？
     * 找到spring-boot-autoconfigure-xx.jar下的/data/mongo，看到MongoDataAutoConfiguration.java
     * mongoTemplate()方法里面就会用到一个MongoConverter，这个convert实际上用的就是MappingMongoConverter，
     * 在mappingMongoConverter()方法里面就用到了MongoCustomConversions，这个conversions怎么来的就是@Import({MongoDataConfiguration.class})
     * 找到MongoDataConfiguration.java，它里面就定义了MongoCustomConversions这个bean，这个bean定义是个不包含任何自定义的Conversions
        @Bean
        @ConditionalOnMissingBean public MongoCustomConversions mongoCustomConversions() {
           return new MongoCustomConversions(Collections.emptyList());
        }
        这个bean定义使用了@ConditionalOnMissingBean，意思是没有这个bean时才会定义这个bean
        所以要使用我们自己的自定义Conversions，只需要自己定义一个这样的bean就行了

     * 由此我们得到一个思路：当我们需要定制一些spring组件的特性时，不妨打开springboot的自动配置，
     * 看看它里面是怎么做的，有没有留下一些口子来定义一些bean
     */
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));// 将MoneyReadConverter作为列表成员加进来
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Coffee espresso = Coffee.builder()
                .name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        Coffee saved = mongoTemplate.save(espresso);
        log.info("Coffee {}", saved);

        List<Coffee> list = mongoTemplate.find(
                Query.query(Criteria.where("name").is("espresso")), Coffee.class);
        log.info("Find {} Coffee", list.size());
        list.forEach(c -> log.info("Coffee {}", c));

        Thread.sleep(1000); //睡一秒，为了看更新时间
        UpdateResult result = mongoTemplate.updateFirst(query(where("name").is("espresso")),
                new Update().set("price", Money.ofMajor(CurrencyUnit.of("CNY"), 30))
                        .currentDate("updateTime"),
                Coffee.class);
        log.info("Update Result:{}", result.getModifiedCount());
        Coffee updateOne = mongoTemplate.findById(saved.getId(), Coffee.class);
        log.info("Update Result:{}", updateOne);
    }
}
