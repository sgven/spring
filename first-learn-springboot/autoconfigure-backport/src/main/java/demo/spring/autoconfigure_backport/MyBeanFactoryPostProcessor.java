package demo.spring.autoconfigure_backport;

import demo.spring.hello.greeting.GreetingApplicationRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.ClassUtils;

@Slf4j
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        boolean hasClass = ClassUtils.isPresent("demo.spring.hello.greeting.GreetingApplicationRunner",
                MyBeanFactoryPostProcessor.class.getClassLoader());
        if (!hasClass) {
            log.info("GreetingApplicationRunner is NOT present in CLASSPATH.");
            return;
        }
        boolean hasBean = beanFactory.containsBeanDefinition("greetingApplicationRunner");
        if (hasBean) {
            log.info("We already have a greetingApplicationRunner bean registered.");
            return;
        }

        register(beanFactory);
    }

    private void register(ConfigurableListableBeanFactory beanFactory) {
        if (beanFactory instanceof BeanDefinitionRegistry) {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(GreetingApplicationRunner.class);
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
            registry.registerBeanDefinition("greetingApplicationRunner", beanDefinition);
        } else {
            beanFactory.registerSingleton("greetingApplicationRunner", new GreetingApplicationRunner());
        }
    }
}
