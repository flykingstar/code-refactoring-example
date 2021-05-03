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
        int volumeCredits = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Statement for %s", invoice.getCustomer()));
        Locale locale = new Locale("en", "US");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            int thisAmount = 0;
            switch (play.getType()) {
                case "tragedy":
                    thisAmount = getTragedyAmount(performance);
                    volumeCredits = getTragedyCredits(volumeCredits, performance);
                    break;
                case "comedy":
                    thisAmount = getComedyAmount(performance);
                    volumeCredits += getComedyCredits(performance);
                     break;
                default:
                    throw new RuntimeException("unknown type:" + play.getType());
            }
            sb.append(String.format(" %s: %s (%d seats)" + "\n", play.getName(), formatAmount(thisAmount, format), performance.getAudience()));
            totalAmount += thisAmount;
        }
        sb.append("Amount owed is ").append(formatAmount(totalAmount, format)).append("\n");
        sb.append("You earned ").append(volumeCredits).append(" credits\n");
        return sb.toString();
    }

    private double getComedyCredits(Performance performance) {
        return Math.max(performance.getAudience() - 30, 0) + Math.floor(performance.getAudience() / 5);
    }

    private int getTragedyCredits(int volumeCredits, Performance performance) {
        volumeCredits += Math.max(performance.getAudience() - 30, 0);
        return volumeCredits;
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

    private String formatAmount(int totalAmount, NumberFormat format) {
        return format.format(totalAmount / 100);
    }
}
