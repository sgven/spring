package demo.spring.instance.unit_test.junit.test;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * 生命周期测试
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)//initAll() must be static unless the test class is annotated with @TestInstance(Lifecycle.PER_CLASS).
public class LifeCycleTests {

    @BeforeAll //类似于JUit4的 @BeforeClass
    void initAll() {
        System.out.println("-----initAll");
    }

    @BeforeEach //类似于JUit4的 @Before
    void init() {
        System.out.println("-----init");
    }

    @Test
    void succeedingTest() {
        System.out.println("-----succeedingTest");
    }

    @Test
    void failingTest() {
        fail("a failing test");
    }

    @Test
    @Disabled   // 禁用test，类似于JUit4的 @Ignore
    void skippedTest() {
        // no execute
    }

    @AfterEach //类似于JUit4的 @After
    void tearDown() {
        System.out.println("-----tearDown");
    }

    @AfterAll //类似于JUit4的 @AfterClass
    void tearDownAll() {
        System.out.println("-----tearDownAll");
    }
}
