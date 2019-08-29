package demo.spring.mongodb_redis.converter;

import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;

public class MoneyToBytesConverter implements Converter<Money, byte[]> {
    @Nullable
    @Override
    public byte[] convert(Money money) {
        String value = Long.toString(money.getAmountMinorLong());
        return value.getBytes(StandardCharsets.UTF_8);
    }
}
