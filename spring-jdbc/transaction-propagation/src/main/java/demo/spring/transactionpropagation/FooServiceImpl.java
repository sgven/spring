package demo.spring.transactionpropagation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事务的传播性，默认是Propagation.REQUIRED
 * 测试
 * 1.Propagation.REQUIRES_NEW：始终启动一个新事务
 *                              两个事务没有关联
 * 2.Propagation.NESTED：在原事务内启动一个内嵌事务
 *                              两个事务有关联
 *                              外部事务回滚，内嵌事务也会回滚
 *                              内嵌事务回滚，不影响外部事务
 * 测试步骤：
 * 1.REQUIRES_NEW，内嵌事务正常提交，外部事务抛异常
 * 结果：外部事务应该不影响内嵌事务 AAA插入失败，BBB插入成功
 * AAA: 0
 * BBB: 1
 *
 * 内嵌事务抛异常，外部事务正常提交，
 * 结果：内嵌事务应该不影响外部事务 AAA插入成功，BBB插入失败
 * AAA: 1
 * BBB: 0
 *
 * 2.NESTED，内嵌事务抛异常，外部事务正常提交
 * 结果：内嵌事务应该不影响外部事务 AAA插入成功，BBB插入失败
 * AAA: 1
 * BBB: 0
 *
 * 内嵌事务正常提交，外部事务抛异常
 * 结果：外部事务回滚，内嵌事务也会回滚 AAA插入失败，BBB插入失败
 * AAA: 0
 * BBB: 0
 */
@Component
@Slf4j
public class FooServiceImpl implements FooService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FooService fooService;

    /**
     * 内嵌事务，插入BBB
     */
    @Override
    @Transactional(rollbackFor = RollbackException.class, propagation = Propagation.REQUIRES_NEW)
//    @Transactional(rollbackFor = RollbackException.class, propagation = Propagation.NESTED)
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('BBB')");
//        throw new RollbackException();
    }

    /**
     * 外部事务，插入AAA
     */
    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public void invokeInsertThenRollback() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
        try {
            fooService.insertThenRollback();
        } catch (RollbackException e) {
            log.error("RollbackException", e);
        }
        throw new RuntimeException();
    }
}
