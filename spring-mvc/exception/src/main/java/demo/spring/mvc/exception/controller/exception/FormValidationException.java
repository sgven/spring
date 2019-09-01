package demo.spring.mvc.exception.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 在异常类上，指定@ResponseStatus(HttpStatus.BAD_REQUEST) HTTP响应码
 * 抛出该异常时，请求中则会返回指定的HTTP响应码
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
@AllArgsConstructor
public class FormValidationException extends RuntimeException {
    private BindingResult result;
}
