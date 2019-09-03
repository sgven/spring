package demo.spring.springboot.autoconfigure_demo;

import demo.spring.hello.greeting.GreetingApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"demo.spring.springboot.autoconfigure_demo","demo.spring.autoconfigure_backport"})
public class AutoconfigureDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoconfigureDemoApplication.class, args);
	}

//	@Bean
//	public GreetingApplicationRunner greetingApplicationRunner() {
//		return new GreetingApplicationRunner("developer");
//	}
}
