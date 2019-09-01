package demo.spring.mvc.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

/**
 * @RestControllerAdvice，类似@RestController
 * 就是@ControllerAdvice + @ResponseBody
 */
@RestControllerAdvice
public class GlobalControllerAdvice {
    /**
     * 由于@RestControllerAdvice中包含了@ResponseBody
     * 所以这里拦截到所有Controller抛出的 ValidationException 异常后，会将结果map作为response body返回
     * @param exception
     * @return
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> validationExceptionHandler(ValidationException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        return map;
    }
}
