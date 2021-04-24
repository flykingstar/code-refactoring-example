package org.coderead;

import com.sun.xml.internal.ws.util.StringUtils;
import org.coderead.model.Invoice;
import org.coderead.model.Performance;
import org.coderead.model.Play;

import java.lang.reflect.InvocationTargetException;
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

    private final TragedyCalculator tragedyCalculator = new TragedyCalculator();
    private final ComedyCalculator comedyCalculator = new ComedyCalculator();
    private Invoice invoice;
    private Map<String, Play> plays;

    public Statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String show() {
        int totalAmount = 0;
        int volumeCredits = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Statement for %s", invoice.getCustomer()));
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            ICalculator calculator = getCalculatorByType(play);
            int thisAmount = calculator.getAmount(performance);
            volumeCredits += calculator.getVolumeCredits(performance);
            stringBuilder.append(String.format(" %s: %s (%d seats)\n", play.getName(), formatAmount(format, thisAmount), performance.getAudience()));
            totalAmount += thisAmount;
        }
        stringBuilder.append(String.format("Amount owed is %s\n", formatAmount(format, totalAmount)));
        stringBuilder.append(String.format("You earned %s credits\n", volumeCredits));
        return stringBuilder.toString();
    }

    private String formatAmount(NumberFormat format, int thisAmount) {
        return format.format(thisAmount / 100);
    }

    private ICalculator getCalculatorByType(Play play) {
        try {
            return (ICalculator) Class.forName(getCalculatorClassName(play.getType())).getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("unknown type:" + play.getType());
        }
    }

    private String getCalculatorClassName(String type) {
        return getPackageName() + "." + StringUtils.capitalize(type) + "Calculator";
    }

    private String getPackageName() {
        return this.getClass().getPackage().getName();
    }

}
