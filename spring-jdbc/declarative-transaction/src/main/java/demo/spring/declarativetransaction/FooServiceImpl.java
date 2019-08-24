package demo.spring.declarativetransaction;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FooServiceImpl implements FooService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FooService fooService;

    @Override
    @Transactional
    public void insertRecord() {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
    }

    /**
     * 实际上，使用注解@Transactional声明式事务的bean方法，spring是通过AOP动态代理增强了带有@Transactional的方法
     * 其他类使用@Autowired注入该bean实例，看似是调用的FooService.insertThenRollback()方法，实际上是调用的AOP代理增强后的方法
     * 所以，在没有@Transactional的方法内部去调用声明了事务的方法，其实调用的就是普通的方法，不是(通过@Autowired)使用spring管理的增强后的代理bean中的方法，
     * 所以，方法1事务不会生效；
     * 解决办法参考方法2
     */
    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('BBB')");
        throw new RollbackException();
    }

    /**
     * 方法1.事务未生效
     * @throws RollbackException
     */
//    @Override
//    public void invokeInsertThenRollback() throws RollbackException {
//        insertThenRollback();
//    }

    /**
     * 方法2.事务生效
     * @throws RollbackException
     */
    @Override
    public void invokeInsertThenRollback() throws RollbackException {
        // 获取当前类的代理对象
//        FooService fooService = (FooService) AopContext.currentProxy();
        // 或者通过@Autowired注入由Spring管理增强后的bean

        // 再调用的代理对象的方法(其实是增强后的方法)，事务就会生效了
        fooService.insertThenRollback();
    }

}
