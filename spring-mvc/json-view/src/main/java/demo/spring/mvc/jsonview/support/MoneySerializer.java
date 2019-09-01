package demo.spring.mvc.jsonview.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.money.Money;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

// 有了这个注解后@JsonComponent，Spring Boot 就会帮我们把序列化和反序列化器注册到Jackson对应的类上去
@JsonComponent
public class MoneySerializer extends StdSerializer<Money> {
    protected MoneySerializer() {
        super(Money.class);
    }

    @Override
    public void serialize(Money money, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // 序列化输出，直接输出bigDecimal金额
        jsonGenerator.writeNumber(money.getAmount());
    }
}
