package demo.spring.errorcode;


import org.springframework.dao.DuplicateKeyException;

/**
 * 自定义异常，必须是DataAccessException的子类
 */
public class CustomDuplicateKeyException extends DuplicateKeyException {
    public CustomDuplicateKeyException(String msg) {
        super(msg);
    }

    public CustomDuplicateKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
