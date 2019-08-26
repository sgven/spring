package demo.spring.jpacomplex;

import demo.spring.jpacomplex.dao.CoffeeDao;
import demo.spring.jpacomplex.dao.OrderDao;
import demo.spring.jpacomplex.entity.Coffee;
import demo.spring.jpacomplex.entity.Order;
import demo.spring.jpacomplex.entity.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@Slf4j
public class JPAComplexApplication implements ApplicationRunner {

	@Autowired
	private CoffeeDao coffeeDao;
	@Autowired
	private OrderDao orderDao;

	public static void main(String[] args) {
		SpringApplication.run(JPAComplexApplication.class, args);
	}

	//开启事务
	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		initOrders();
		findOrders();
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
				.state(OrderState.INIT)
				.build();
		orderDao.save(order);
		log.info("Order: {}", order);

		order = Order.builder()
				.customer("Li Lei")
				.items(Arrays.asList(espresso, latte))
				.state(OrderState.INIT)
				.build();
		orderDao.save(order);
		log.info("Order: {}", order);
	}

	private void findOrders() {
		coffeeDao
				.findAll(Sort.by(Sort.Direction.DESC, "id"))
				.forEach(c -> log.info("Loading {}", c));

		List<Order> list = orderDao.findTop3ByOrderByUpdateTimeDescIdAsc();
		log.info("findTop3ByOrderByUpdateTimeDescIdAsc: {}", getJoinedOrderId(list));

		list = orderDao.findByCustomerOrderById("Li Lei");
		log.info("findByCustomerOrderById: {}", getJoinedOrderId(list));

		// 不开启事务会因为没Session而报LazyInitializationException
		list.forEach(o -> {
			log.info("Order {}", o.getId());
			o.getItems().forEach(i -> log.info("  Item {}", i));
		});

		list = orderDao.findByItems_Name("latte");
		log.info("findByItems_Name: {}", getJoinedOrderId(list));
	}

	private String getJoinedOrderId(List<Order> list) {
		return list.stream().map(o -> o.getId().toString())
				.collect(Collectors.joining(","));
	}
}
