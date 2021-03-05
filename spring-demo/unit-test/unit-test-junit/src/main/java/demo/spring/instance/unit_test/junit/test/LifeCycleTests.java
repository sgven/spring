package demo.spring.instance.unit_test.junit.test;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * 生命周期测试
 * PER_CLASS: 使用此模式时，每个测试类将创建一个新的测试实例
 * //@Nested嵌套类（inner class）里面想使用  @BeforeAll、@AfterAll，就必须为PER_CLASS模式，
 * 因为inner class不能有static成员
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
        System.out.println("skipped");
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
