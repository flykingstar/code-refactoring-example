package org.coderead;

import com.sun.xml.internal.ws.util.StringUtils;
import org.coderead.model.Invoice;
import org.coderead.model.Performance;
import org.coderead.model.Play;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * 客户服务类
 *
 * @author kendoziyu
 * @since 2020/10/11 0011
 */
public class Statement {

    private final ComedyCalculator comedyCalculator = new ComedyCalculator();
    private Invoice invoice;
    private Map<String, Play> plays;

    public Statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String show() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Statement for %s", invoice.getCustomer()));
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        dealThisAmount(sb, format);
        dealTotalAmount(sb, format);
        dealCredits(sb);
        return sb.toString();
    }

    private void dealCredits(StringBuilder sb) {
        int volumeCredits = 0;
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            ICalculator calculator = getCalculatorByType(play.getType());
            volumeCredits += calculator.getCredits(performance);
        }
        sb.append("You earned ").append(volumeCredits).append(" credits\n");
    }

    private void dealTotalAmount(StringBuilder sb, NumberFormat format) {
        int totalAmount = 0;
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            ICalculator calculator = getCalculatorByType(play.getType());
            totalAmount += calculator.getAmount(performance);
        }
        sb.append("Amount owed is ").append(formatAmount(totalAmount, format)).append("\n");
    }

    private void dealThisAmount(StringBuilder sb, NumberFormat format) {
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            ICalculator calculator = getCalculatorByType(play.getType());
            sb.append(String.format(" %s: %s (%d seats)" + "\n", play.getName(), formatAmount(calculator.getAmount(performance), format), performance.getAudience()));
        }
    }

    private ICalculator getCalculatorByType(String type) {
        try {
            return (ICalculator) Class.forName("org.coderead" + "." + StringUtils.capitalize(type) + "Calculator").getConstructor().newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("unknown type:" + type);
        }
    }


    private String formatAmount(int totalAmount, NumberFormat format) {
        return format.format(totalAmount / 100);
    }
}
