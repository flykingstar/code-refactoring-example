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

    private Invoice invoice;
    private Map<String, Play> plays;

    public Statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String show() {
        int totalAmount = 0;
        double volumeCredits = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Statement for %s", invoice.getCustomer()));

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            double tempVolumeCredits = 0;
            AbstractCalculator calculatorInterface = AbstractCalculator.getInterfaceByType(play);
            tempVolumeCredits += calculatorInterface.getCredits(performance);
            volumeCredits += tempVolumeCredits;
        }
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            totalAmount += getThisAmount(performance, play);
        }
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            stringBuilder.append(String.format(" %s: %s (%d seats)\n", play.getName(), formatUSD(format, getThisAmount(performance, play)), performance.getAudience()));
        }
        stringBuilder.append(String.format("Amount owed is %s\n", formatUSD(format, totalAmount)));
        stringBuilder.append(String.format("You earned %s credits\n", volumeCredits));
        return stringBuilder.toString();
    }

    private double getThisAmount(Performance performance, Play play) {
        AbstractCalculator calculatorInterface = AbstractCalculator.getInterfaceByType(play);
        return calculatorInterface.getAmount(performance);
    }

    public String formatUSD(NumberFormat format, double thisAmount) {
        return format.format(thisAmount / 100);
    }
}
