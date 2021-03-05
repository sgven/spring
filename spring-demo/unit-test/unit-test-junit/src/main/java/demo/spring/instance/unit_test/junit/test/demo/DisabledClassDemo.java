package demo.spring.instance.unit_test.junit.test.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 禁用测试class
 */
@Disabled("Disabled until bug #99 has been fixed")
class DisabledClassDemo {

    @Test
    void testWillBeSkipped() {
        System.out.println("test skipped");
    }
}
