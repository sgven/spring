package demo.spring.jedis;

import demo.spring.jedis.model.Coffee;
import demo.spring.jedis.repository.CoffeeRepository;
import demo.spring.jedis.service.CoffeeOrderService;
import demo.spring.jedis.service.CoffeeService;
import demo.spring.jedis.model.CoffeeOrder;
import demo.spring.jedis.model.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.Map;
import java.util.Optional;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
public class SpringJedisApplication implements ApplicationRunner {
//	@Autowired
//	private CoffeeRepository coffeeRepository;
	@Autowired
	private CoffeeService coffeeService;
//	@Autowired
//	private CoffeeOrderService orderService;
	@Autowired
	private JedisPoolConfig jedisPoolConfig;
	@Autowired
	private JedisPool jedisPool;

	public static void main(String[] args) {
		SpringApplication.run(SpringJedisApplication.class, args);
	}

	@Bean
	@ConfigurationProperties("redis")
	public JedisPoolConfig jedisPoolConfig() {
		return new JedisPoolConfig();
	}

	@Bean(destroyMethod = "close")
	public JedisPool jedisPool(@Value("${redis.host}") String host) {
		return new JedisPool(jedisPoolConfig(), host);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info(jedisPoolConfig.toString());

		try (Jedis jedis = jedisPool.getResource()) {
			coffeeService.findAllCoffee().forEach(c -> {
				jedis.hset("springbucks-menu",
						c.getName(),
						Long.toString(c.getPrice().getAmountMinorLong()));
			});

			Map<String, String> menu = jedis.hgetAll("springbucks-menu");
			log.info("Menu: {}", menu);

			String price = jedis.hget("springbucks-menu", "espresso");
			log.info("espresso - {}",
					Money.ofMinor(CurrencyUnit.of("CNY"), Long.parseLong(price)));
		}
	}
}

