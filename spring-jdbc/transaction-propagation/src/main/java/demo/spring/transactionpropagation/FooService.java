package demo.spring.transactionpropagation;

public interface FooService {
    void insertThenRollback() throws RollbackException;
    void invokeInsertThenRollback() throws RollbackException;
}
