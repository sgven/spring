package demo.spring.web.customer.resttemplate;

import demo.spring.web.customer.resttemplate.model.Coffee;
import demo.spring.web.customer.resttemplate.support.CustomConnectionKeepAliveStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class AdvancedResttemplateDemoApplication implements ApplicationRunner {

	@Autowired
	private RestTemplate restTemplate;

	public static void main(String[] args) {
//		SpringApplication.run(AdvancedResttemplateDemoApplication.class, args);
		new SpringApplicationBuilder()
				.sources(AdvancedResttemplateDemoApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.web(WebApplicationType.NONE)
				.run(args);
	}

	@Bean
	public HttpComponentsClientHttpRequestFactory requestFactory() {
		PoolingHttpClientConnectionManager connectionManager =
				new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
		connectionManager.setMaxTotal(200);
		connectionManager.setDefaultMaxPerRoute(20);//每个route最大20个连接

		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connectionManager)
				.evictIdleConnections(30, TimeUnit.SECONDS)
				//禁用HttpClients的自动重试，避免一些敏感场景下的问题。比如重复打款
				//保险的做法是：下游系统做幂等，上游系统禁止自动重试，所以这里最好就是禁用掉自动重试
				.disableAutomaticRetries()
				//官方默认的策略，有 Keep-Alive 取里面的值，没有的话永久有效
//				.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
				//换成自定义的策略
				.setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
				.build();

		HttpComponentsClientHttpRequestFactory requestFactory =
				new HttpComponentsClientHttpRequestFactory(httpClient);

		return requestFactory;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
//		return new RestTemplate();

		return builder
				.setConnectTimeout(Duration.ofMillis(100))
				.setReadTimeout(Duration.ofMillis(500))
				.requestFactory(this::requestFactory)
				.build();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		URI uri = UriComponentsBuilder
				.fromUriString("http://localhost:8080/coffee/?name={name}")
				.build("mocha");
		RequestEntity<Void> req = RequestEntity.get(uri)
				.accept(MediaType.APPLICATION_XML)//Request Headers-->指定Accept头为xml
				.build();
		log.info("Request Headers: {}", req.getHeaders());
		ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
		log.info("Response Status: {}, Response Headers: {}", resp.getStatusCode(), resp.getHeaders().toString());
		log.info("Coffee: {}", resp.getBody());

		String coffeeUri = "http://localhost:8080/coffee/";
		Coffee request = Coffee.builder()
				.name("Americano")
				.price(Money.of(CurrencyUnit.of("CNY"), 25.00))
				.build();
		Coffee response = restTemplate.postForObject(coffeeUri, request, Coffee.class);
		log.info("New Coffee: {}", response);

		//通过ParameterizedTypeReference，指定List泛型
		ParameterizedTypeReference<List<Coffee>> ptr =
				new ParameterizedTypeReference<List<Coffee>>() {};
		ResponseEntity<List<Coffee>> list = restTemplate
				.exchange(coffeeUri, HttpMethod.GET, null, ptr);
		list.getBody().forEach(c -> log.info("Coffee: {}", c));
	}
}
