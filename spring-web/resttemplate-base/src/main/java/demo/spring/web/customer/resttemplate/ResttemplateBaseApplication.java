package demo.spring.web.customer.resttemplate;

import demo.spring.web.customer.resttemplate.model.Quote;
import demo.spring.web.customer.resttemplate.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@SpringBootApplication
@Slf4j
public class ResttemplateBaseApplication implements ApplicationRunner {

	@Autowired
	private RestTemplate restTemplate;

	public static void main(String[] args) {
//		SpringApplication.run(ResttemplateBaseApplication.class, args);
		new SpringApplicationBuilder()
				.sources(ResttemplateBaseApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.web(WebApplicationType.NONE)
				.run(args);
	}

	/**
	 * springboot没有帮我们自动创建RestTemplate，
	 * 但springboot的自动配置有一个RestTemplate自动配置类：RestTemplateAutoConfiguration
	 * 它帮我们自动创建了 RestTemplateBuilder，
	 * 它会从spring上下文取到 converters和Customizer，然后塞到 RestTemplateBuilder 中， 并调用Customizer，实现定制化的开发。
	 * 所以，可以在上下文中配置一些 RestTemplateCustomizer，来定制builder。
	 *
	 * 这里只是简单使用，没有配置RestTemplateCustomizer
	 * @param builder
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
//		return new RestTemplate();
		return builder.build();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//spring官网示例
		Quote quote = restTemplate.getForObject(
				"https://quoters.apps.pcfone.io/api/random", Quote.class);
		log.info(quote.toString());

		//我们自己的RESTFul web服务
		//get实体
		URI uri = UriComponentsBuilder
				.fromUriString("http://localhost:8080/coffee/{id}")
				.build(1);
		ResponseEntity<Coffee> c = restTemplate.getForEntity(uri, Coffee.class);
		log.info("Response Status: {}, Response Headers: {}", c.getStatusCode(), c.getHeaders().toString());
		log.info("Coffee: {}", c.getBody());

		//post实体
		String coffeeUri = "http://localhost:8080/coffee/";
		Coffee request = Coffee.builder()
				.name("Americano")
				.price(BigDecimal.valueOf(25.00))
				.build();
		Coffee response = restTemplate.postForObject(coffeeUri, request, Coffee.class);
		log.info("New Coffee: {}", response);

		//get所有
		String s = restTemplate.getForObject(coffeeUri, String.class);
		log.info("String: {}", s);
	}
}
