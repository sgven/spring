package demo.spring.springboot.property_source;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Spring Boot会找到EnvironmentPostProcessor类的实现，然后把它们加载进来，
 * 所以这个类不需要加@Component注解来把它声明为spring容器中的bean
 * 注意：要在/resources/META-INF/spring.factories文件中，指定EnvironmentPostProcessor的实现类，比如：
 * org.springframework.boot.env.EnvironmentPostProcessor=demo.spring.springboot.property_source.YapfEnvironmentPostProcessor
 */
@Slf4j
public class YapfEnvironmentPostProcessor implements EnvironmentPostProcessor {
    private PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        Resource resource = new ClassPathResource("yapf.properties");
        try {
            // 通过PropertiesPropertySourceLoader.load来把一个Resource变为一个PropertySource
            PropertySource ps = loader.load("YetAnotherPropertiesFile", resource)
                    .get(0);
            propertySources.addFirst(ps);
        } catch (Exception e) {
            log.error("Exception!", e);
        }
    }
}
