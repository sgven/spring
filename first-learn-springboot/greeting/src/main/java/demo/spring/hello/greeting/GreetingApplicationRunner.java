package demo.spring.hello.greeting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@Slf4j
public class GreetingApplicationRunner implements ApplicationRunner {
    private String name;
    public GreetingApplicationRunner() {
        this("autoconfig");
    }

    public GreetingApplicationRunner(String name) {
        this.name = name;
        log.info("Initializing GreetingApplicationRunner By " + name);
    }

    public void run(ApplicationArguments args) throws Exception {
        log.info("Hello everyone! We all like Spring! ");
    }
}
