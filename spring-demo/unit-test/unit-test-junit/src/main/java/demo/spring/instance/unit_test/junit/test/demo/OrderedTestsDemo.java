package demo.spring.instance.unit_test.junit.test.demo;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * 默认情况下，JUnit有一套算法，默认按算法排好的顺序执行测试方法
 * 测试执行顺序，按@Order指定的顺序执行
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderedTestsDemo {

    @Test
    @Order(1)
    void nullValues() {
        // perform assertions against null values
        System.out.println("1");
    }

    @Test
    @Order(2)
    void emptyValues() {
        // perform assertions against empty values
        System.out.println("2");
    }

    @Test
    @Order(3)
    void validValues() {
        // perform assertions against valid values
        System.out.println("3");
    }

}
