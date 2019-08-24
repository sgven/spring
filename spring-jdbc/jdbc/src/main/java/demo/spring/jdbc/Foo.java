package demo.spring.jdbc;

import lombok.Builder;
import lombok.Data;

/**
 * @Data: 帮助生成setter、getter方法
 * @Builder: 生成构造方法
 */
@Data
@Builder
public class Foo {
    private Long id;
    private String bar;
}
