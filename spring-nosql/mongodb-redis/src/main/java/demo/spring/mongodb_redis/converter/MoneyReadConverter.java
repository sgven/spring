package demo.spring.mongodb_redis.converter;

import org.bson.Document;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class MoneyReadConverter implements Converter<Document, Money> {
    @Nullable
    @Override
    public Money convert(Document source) {
        Document money = (Document) source.get("money");
        String currency = ((Document) money.get("currency")).getString("code");
        double amount = Double.parseDouble(money.getString("amount"));
        return Money.of(CurrencyUnit.of(currency), amount);
    }
}
