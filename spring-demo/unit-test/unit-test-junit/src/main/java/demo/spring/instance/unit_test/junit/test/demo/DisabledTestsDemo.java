package demo.spring.instance.unit_test.junit.test.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 禁用测试--method
 * 通过IDEA工具，运行class才有效果
 */
class DisabledTestsDemo {

    @Disabled("Disabled until bug #42 has been resolved")
    @Test
    void testWillBeSkipped() {
        System.out.println("skipped");
    }

    @Test
    void testWillBeExecuted() {
        System.out.println("executed");
    }
}
