package demo.spring.mongodb_redis;

import demo.spring.mongodb_redis.converter.BytesToMoneyConverter;
import demo.spring.mongodb_redis.converter.MoneyReadConverter;
import demo.spring.mongodb_redis.converter.MoneyToBytesConverter;
import demo.spring.mongodb_redis.entity.Coffee;
import demo.spring.mongodb_redis.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.redis.core.convert.RedisCustomConversions;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class MongodbRedisApplication implements ApplicationRunner {

	@Autowired
	private CoffeeService coffeeService;

	public static void main(String[] args) {
		SpringApplication.run(MongodbRedisApplication.class, args);
	}

	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));// 将MoneyReadConverter作为列表成员加进来
	}

	@Bean
	public RedisCustomConversions redisCustomConversions() {
		return new RedisCustomConversions(Arrays.asList(new MoneyToBytesConverter(), new BytesToMoneyConverter()));
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Coffee latte = Coffee.builder()
				.name("latte")
				.price(Money.of(CurrencyUnit.of("CNY"), 29))
				.build();
		coffeeService.save(latte);
		coffeeService.findByName("latte");
		coffeeService.findByName("latte");
		coffeeService.deleteAll();
	}
}
