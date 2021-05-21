package demo.spring.web.customer.resttemplate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

//有参构造函数，才用@Builder
//@Builder
//@AllArgsConstructor

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    private String type;
    private Value value;

    @Override
    public String toString() {
        return "Quote{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
