package demo.spring.cache;

import demo.spring.cache.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
// 标明了会拦截类的执行，通过这里可以知道spring缓存抽象的运行机制是基于AOP的，对加了注解的方法的执行会做一个拦截
@EnableCaching(proxyTargetClass = true)
//@EnableCaching // 效果是一样的 proxyTargetClass true表示使用cglib动态代理，false表示使用jdk动态代理
public class SpringCacheApplication implements ApplicationRunner {
	@Autowired
	private CoffeeService coffeeService;

	public static void main(String[] args) {
		SpringApplication.run(SpringCacheApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("Count: {}", coffeeService.findAllCoffee().size());
		for (int i = 0; i < 10; i++) {
			log.info("Reading from cache.");
			coffeeService.findAllCoffee();
		}
		coffeeService.reloadCoffee();
		log.info("Reading after refresh.");
		coffeeService.findAllCoffee().forEach(c -> log.info("Coffee {}", c.getName()));
	}
}

