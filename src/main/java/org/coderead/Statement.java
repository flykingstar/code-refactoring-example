package org.coderead;

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
        formatThisAmount(stringBuilder, NumberFormat.getCurrencyInstance(new Locale("en", "US")), plays);
        formatTotalAmount(stringBuilder, NumberFormat.getCurrencyInstance(new Locale("en", "US")));
        formatVolumeCredits(stringBuilder, volumeCredits);
        return stringBuilder.toString();
    }

    private void formatVolumeCredits(StringBuilder stringBuilder, int volumeCredits) {
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            CalculatorAbstract calculator = CalculatorAbstract.getCalculatorByType(play);
            volumeCredits += calculator.getVolumeCredits(performance);
        }
        stringBuilder.append(String.format("You earned %s credits\n", volumeCredits));
    }

    private void formatTotalAmount(StringBuilder stringBuilder, NumberFormat format) {
        int totalAmount = 0;
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            CalculatorAbstract calculator = CalculatorAbstract.getCalculatorByType(play);
            totalAmount += calculator.getAmount(performance);
        }
        stringBuilder.append(String.format("Amount owed is %s\n", formatAmount(format, totalAmount)));
    }

    private void formatThisAmount(StringBuilder stringBuilder, NumberFormat format, Map<String, Play> plays) {
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            CalculatorAbstract calculator = CalculatorAbstract.getCalculatorByType(play);
            int thisAmount = calculator.getAmount(performance);
            stringBuilder.append(String.format(" %s: %s (%d seats)\n", play.getName(), formatAmount(format, thisAmount), performance.getAudience()));
        }
    }

    private String formatAmount(NumberFormat format, int thisAmount) {
        return format.format(thisAmount / 100);
    }

}
