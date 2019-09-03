package demo.spring.springboot.my_autoconfigure;

import demo.spring.hello.greeting.GreetingApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 当classpath中有GreetingApplicationRunner这个类，(Configuration) 才生效
@ConditionalOnClass(GreetingApplicationRunner.class)
public class MyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(GreetingApplicationRunner.class)
    /**
     * havingValue：比较从配置读取到的属性值与havingValue给定的值是否相同，相同才生效
     * matchIfMissing：缺少该property时是否匹配上，设置为true，则没有该property时也能匹配上，不过如果有值但设置的值与havingValue不一致，还是不会匹配上，不会生效。
     */
    @ConditionalOnProperty(name = "greeting.enabled", havingValue = "true", matchIfMissing = true)
//    @ConditionalOnProperty(name = "greeting.enabled", havingValue = "true")
    public GreetingApplicationRunner greetingApplicationRunner() {
        return new GreetingApplicationRunner();
    }

}
