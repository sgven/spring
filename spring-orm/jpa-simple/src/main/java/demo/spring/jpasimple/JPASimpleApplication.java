package demo.spring.jpasimple;

import demo.spring.jpasimple.dao.CoffeeDao;
import demo.spring.jpasimple.dao.OrderDao;
import demo.spring.jpasimple.entity.Coffee;
import demo.spring.jpasimple.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class JPASimpleApplication implements ApplicationRunner {

	@Autowired
	private CoffeeDao coffeeDao;
	@Autowired
	private OrderDao orderDao;

	public static void main(String[] args) {
		SpringApplication.run(JPASimpleApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		initOrders();
	}

	private void initOrders() {
		Coffee espresso = Coffee.builder().name("espresso")
				.price(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.build();
		coffeeDao.save(espresso);
		log.info("Coffee: {}", espresso);

		Coffee latte = Coffee.builder().name("latte")
				.price(Money.of(CurrencyUnit.of("CNY"), 30.0))
				.build();
		coffeeDao.save(latte);
		log.info("Coffee: {}", latte);

		Order order = Order.builder()
				.customer("Li Lei")
				.items(Collections.singletonList(espresso))
				.state(0)
				.build();
		orderDao.save(order);
		log.info("Order: {}", order);

		order = Order.builder()
				.customer("Li Lei")
				.items(Arrays.asList(espresso, latte))
				.state(0)
				.build();
		orderDao.save(order);
		log.info("Order: {}", order);
	}
}
