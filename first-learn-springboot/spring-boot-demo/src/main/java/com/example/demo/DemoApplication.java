package com.example.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//@PropertySource(value = "classpath:application.properties")
@PropertySource(value = "application.properties")
@SpringBootApplication
//@ComponentScan(basePackages="com.example.demo")
public class DemoApplication {

	public static void main(String[] args) {
//		SpringApplication app = new SpringApplication(DemoApplication.class);
//		app.setBannerMode(Banner.Mode.OFF);
//		app.run(args);
		SpringApplication.run(DemoApplication.class, args);
	}

}
