package demo.spring.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DataSourceApplication implements CommandLineRunner{

	@Autowired
	DataSourceDemo demo;

	public static void main(String[] args) {
		SpringApplication.run(DataSourceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		demo.showConnection();
		demo.showData();
	}

}
