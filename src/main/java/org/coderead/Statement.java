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
        formatThisAmount(stringBuilder);
        formatTotalAmount(stringBuilder);
        formatCredits(stringBuilder);
        return stringBuilder.toString();
    }

    private void formatCredits(StringBuilder stringBuilder) {
        double volumeCredits = 0;
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            volumeCredits += getVolumeCredits(performance, play);
        }
        stringBuilder.append(String.format("You earned %s credits\n", volumeCredits));
    }

    private void formatTotalAmount(StringBuilder stringBuilder) {
        int totalAmount = 0;
        for (Performance performance1 : invoice.getPerformances()) {
            Play play1 = plays.get(performance1.getPlayId());
            totalAmount += getThisAmount(performance1, play1);
        }
        stringBuilder.append(String.format("Amount owed is %s\n", formatUSD(totalAmount)));
    }

    private void formatThisAmount(StringBuilder stringBuilder) {
        for (Performance performance2 : invoice.getPerformances()) {
            Play play2 = plays.get(performance2.getPlayId());
            int thisAmount = getThisAmount(performance2, play2);
            stringBuilder.append(String.format(" %s: %s (%d seats)\n", play2.getName(),
                    formatUSD(thisAmount), performance2.getAudience()));
        }
    }

    private double getVolumeCredits(Performance performance, Play play) {
        double tempCredits = 0;
        if ("tragedy".equals(play.getType())) {
            tempCredits = getTragedyVolumeCredits(performance);
        }
        if ("comedy".equals(play.getType())) {
            tempCredits = getComedyVolumeCredits(performance);
        }
        return tempCredits;
    }

    private String formatUSD(int thisAmount) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(thisAmount / 100);
    }

    private int getThisAmount(Performance performance, Play play) {
        int thisAmount;
        switch (play.getType()) {
            case "tragedy":
                thisAmount = getTragedyAmount(performance);
                break;
            case "comedy":
                thisAmount = getComedyAmount(performance);
                break;
            default:
                throw new RuntimeException("unknown type:" + play.getType());
        }
        return thisAmount;
    }

    private double getComedyVolumeCredits(Performance performance) {
        return Math.max(performance.getAudience() - 30, 0) + Math.floor(performance.getAudience() / 5);
    }

    private int getTragedyVolumeCredits(Performance performance) {
        return Math.max(performance.getAudience() - 30, 0);
    }

    private int getComedyAmount(Performance performance) {
        int thisAmount;
        thisAmount = 30000;
        if (performance.getAudience() > 20) {
            thisAmount += 10000 + 500 * (performance.getAudience() - 20);
        }
        thisAmount += 300 * performance.getAudience();
        return thisAmount;
    }

    private int getTragedyAmount(Performance performance) {
        int thisAmount;
        thisAmount = 40000;
        if (performance.getAudience() > 30) {
            thisAmount += 1000 * (performance.getAudience() - 30);
        }
        return thisAmount;
    }
}
