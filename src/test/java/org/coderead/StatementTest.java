package org.coderead;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.coderead.model.Invoice;
import org.coderead.model.Play;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * 新建类
 *
 * @author kendoziyu
 * @since 2020/10/11 0011
 */
public class StatementTest {

    @Test
    public void test() {
        String expected = "Statement for BigCo Hamlet: $650.00 (55.0 seats)\n" +
                " As You Like It: $580.00 (35.0 seats)\n" +
                " ji-xie-shi: $580.00 (35.0 seats)\n" +
                " bai-she-zhuan: $580.00 (35.0 seats)\n" +
                " Othello: $500.00 (40.0 seats)\n" +
                "Amount owed is $2,890.00\n" +
                "You earned 71.0 credits\n";
        final String plays = "{" +
                "\"hamlet\":{\"name\":\"Hamlet\",\"type\":\"tragedy\"}," +
                "\"as-like\":{\"name\":\"As You Like It\",\"type\":\"comedy\"}," +
                "\"ji-xie-shi\":{\"name\":\"ji-xie-shi\",\"type\":\"action\"}," +
                "\"bai-she-zhuan\":{\"name\":\"bai-she-zhuan\",\"type\":\"love\"}," +
                "\"othello\":{\"name\":\"Othello\",\"type\":\"tragedy\"}" +
                "}";

        final String invoices = "{" +
                "\"customer\":\"BigCo\",\"performances\":[" +
                "{\"playId\":\"hamlet\",\"audience\":55}" +
                "{\"playId\":\"as-like\",\"audience\":35}" +
                "{\"playId\":\"ji-xie-shi\",\"audience\":35}" +
                "{\"playId\":\"bai-she-zhuan\",\"audience\":35}" +
                "{\"playId\":\"othello\",\"audience\":40}" +
                "]" +
                "}";

        TypeReference<Map<String, Play>> typeReference = new TypeReference<Map<String, Play>>(){};
        Map<String, Play> playMap = JSONObject.parseObject(plays, typeReference);
        Invoice invoice = JSONObject.parseObject(invoices, Invoice.class);
        Statement statement = new Statement(invoice, playMap);
        String result = statement.show();
        Assert.assertEquals(expected, result);
    }
}
