package demo.spring.mvc.appcontext.foo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

/**
 * 切面 ，拦截所有testBean打头的Bean，并没有指定拦截某个具体方法，所以会在它所有方法执行完之后拦截-->执行打印
 */
@Aspect
@Slf4j
public class FooAspect {
    @AfterReturning("bean(testBean*)")
    public void printAfter() {
        log.info("after hello()");
    }
}
