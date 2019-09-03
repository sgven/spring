package demo.spring.autoconfigure_backport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAutoConfiguration {

    // 注意：定义BeanFactoryPostProcessor类型的Bean，需要把定义它这个bean的方法定义为static的，否则spring启动的时候会有warning
    @Bean
    public static MyBeanFactoryPostProcessor myBeanFactoryPostProcessor() {
        return new MyBeanFactoryPostProcessor();
    }
}
