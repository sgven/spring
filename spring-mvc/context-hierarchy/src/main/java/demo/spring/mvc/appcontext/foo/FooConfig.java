package demo.spring.mvc.appcontext.foo;

import demo.spring.mvc.appcontext.context.TestBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 配置类，相当于一个上下文foo context，配置了两个TestBean，两个TestBean传入的context都是foo
 * 声明了一个AOP切面FooAspect，并开启了@EnableAspectJAutoProxy切面支持
 */
@Configuration
@EnableAspectJAutoProxy
public class FooConfig {
    @Bean
    public TestBean testBeanX() {
        return new TestBean("foo");
    }

    @Bean
    public TestBean testBeanY() {
        return new TestBean("foo");
    }

    @Bean
    public FooAspect fooAspect() {
        return new FooAspect();
    }
}
