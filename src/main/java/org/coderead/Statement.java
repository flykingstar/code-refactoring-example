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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Statement for %s", invoice.getCustomer()));
        stringBuilder.append(formatPerformances());
        stringBuilder.append(String.format("Amount owed is %s\n", formatUSD(getTotalAmount())));
        stringBuilder.append(String.format("You earned %s credits\n", getVolumeCredits()));
        return stringBuilder.toString();
    }

    private StringBuilder formatPerformances() {
        StringBuilder stringBuilder1 = new StringBuilder();
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            stringBuilder1.append(String.format(" %s: %s (%s seats)\n", play.getName(), formatUSD(getThisAmount(performance, play)), performance.getAudience()));
        }
        return stringBuilder1;
    }

    private double getVolumeCredits() {
        double volumeCredits = 0;
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            volumeCredits += getVolumeCredits(performance, play);
        }
        return volumeCredits;
    }

    private int getTotalAmount() {
        int totalAmount = 0;
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            totalAmount += getThisAmount(performance, play);
        }
        return totalAmount;
    }

    private double getVolumeCredits(Performance performance, Play play) {
        return getCalculatorInterface(play).getVolumeCredits(performance);
    }

    private ICalculatorInterface getCalculatorInterface(Play play) {
        ICalculatorInterface calculatorInterface = null;
        if ("tragedy".equals(play.getType())) {
            calculatorInterface = new TragedyCalcultor();
        }

        if ("comedy".equals(play.getType())) {
            calculatorInterface = new ComedyCalculator();
        }
        return calculatorInterface;
    }

    private double getThisAmount(Performance performance, Play play) {
        return getCalculatorInterface(play).getAmount(performance);
    }

    private String formatUSD(double amount) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(amount / 100);
    }

}
