package demo.spring.instance.unit_test.junit.test;

import demo.spring.instance.unit_test.junit.business.Person;
import demo.spring.instance.unit_test.junit.test.autowire.MyParameterResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 构造函数和方法中的依赖注入--测试
 * 必须通过@ExtendWith注册适当的扩展名，才能显式启用其他参数解析器
 */
@ExtendWith(MyParameterResolver.class)
public class AutowiredTests {

    public AutowiredTests(Person person) {//No ParameterResolver registered for parameter
    }

    @BeforeEach
    void init(Person person) {
//        person.setFirstName("xie");//set无效，test1()，person中name是null
    }

    @Test
    @DisplayName("依赖注入Person")
    void test1(Person person) {
        assertTrue(person != null);
    }
}
