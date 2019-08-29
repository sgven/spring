package demo.spring.mongo.repository.converter;

import org.bson.Document;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

/**
 * 这里并没有实现如何将Money转换成MongoDB中的Document数据结构
 * 是因为MongoDB会帮我们自动将Money转换成Document（类似JSON的BSON数据结构）
 * JSON和BSON的区别：
 * json是像字符串一样存储的，bson是按结构存储的
 * 参考：https://blog.csdn.net/xiaojin21cen/article/details/60953980
 */
public class MoneyReadConverter implements Converter<Document, Money> {
    @Nullable
    @Override
    public Money convert(Document source) {
        Document money = (Document) source.get("money");
        double amount = Double.parseDouble(money.getString("amount"));// 金额
        String currency = ((Document) money.get("currency")).getString("code");// 币种
        // 通过currency和amount拼出Money对象
        return Money.of(CurrencyUnit.of(currency), amount);
    }
}
