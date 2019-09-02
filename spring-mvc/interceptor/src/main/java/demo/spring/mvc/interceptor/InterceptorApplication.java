package demo.spring.mvc.interceptor;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import demo.spring.mvc.interceptor.controller.PerformanceInteceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
public class InterceptorApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(InterceptorApplication.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 添加了一个PerformanceInteceptor
		registry.addInterceptor(new PerformanceInteceptor())
				// 指定要拦截/coffee/** /order/**下的所有url请求调用
				.addPathPatterns("/coffee/**").addPathPatterns("/order/**");
	}

	@Bean
	public Hibernate5Module hibernate5Module() {
		return new Hibernate5Module();
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jacksonBuilderCustomizer() {
		return builder -> {
			builder.indentOutput(true); // 缩进
			builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai")); // 时区
		};
	}
}
