package demo.spring.mvc.appcontext.context;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TestBean就是我们要增强的目标
 */
@AllArgsConstructor
@Slf4j
public class TestBean {
    private String context;

    public void hello() {
        log.info("hello " + context);
    }
}
