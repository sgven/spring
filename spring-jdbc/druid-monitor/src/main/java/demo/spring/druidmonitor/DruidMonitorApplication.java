package demo.spring.druidmonitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
@EnableTransactionManagement(proxyTargetClass = true) // proxyTargetClass true表示使用cglib动态代理，false表示使用jdk动态代理
public class DruidMonitorApplication implements CommandLineRunner {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private FooService fooService;

	public static void main(String[] args) {
		SpringApplication.run(DruidMonitorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info(dataSource.toString());
		new Thread(() -> fooService.selectForUpdate()).start();
		new Thread(() -> fooService.selectForUpdate()).start();
	}
}

