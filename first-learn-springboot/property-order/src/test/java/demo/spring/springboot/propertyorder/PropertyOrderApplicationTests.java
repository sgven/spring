package demo.spring.springboot.propertyorder;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@SpringBootTest(value = { "name=xiejun-test", "sex=1" })// 這就跟在@Value直接定义value的值一样，去覆盖配置文件中的值@Value(value = "name=haha")
@Slf4j
public class PropertyOrderApplicationTests {

	@Value("${name}")
	private String name;

	@Test
	public void contextLoads() {
		// 配置加载顺序：file/config > classpath/config > classpath根路径下的properties
		// 可以通过 spring.profiles.active=dev 激活指定环境的配置文件
		log.info("测试========name="+name);
	}

}
