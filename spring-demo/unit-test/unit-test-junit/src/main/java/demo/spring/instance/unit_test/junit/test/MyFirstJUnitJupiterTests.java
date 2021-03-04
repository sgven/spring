package demo.spring.instance.unit_test.junit.test;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 第一个JUnit5单元测试用例
 */
public class MyFirstJUnitJupiterTests {

    @Test
    void myFirstTest() {
//        Assertions.assertEquals(2, 1+1);
        //为什么这么引用呢？ 因为Assertions是个大而全的工具类，这样导入静态方法，则按需引用，测试类变小了。
        assertEquals(2, 1+1);
    }
}
