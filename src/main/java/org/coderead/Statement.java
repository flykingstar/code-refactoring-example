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
        Locale locale = new Locale("en", "US");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            int thisAmount = 0;
            switch (play.getType()) {
                case "tragedy":
                    thisAmount = getTragedyAmount(performance);
                    volumeCredits += getTragedyCredits(performance);
                    break;
                case "comedy":
                    thisAmount = getComedyAmount(performance);
                    volumeCredits += getComedyCredits(performance);
                    break;
                default:
                    throw new RuntimeException("unknown type:" + play.getType());
            }

            stringBuilder.append(String.format(" %s: %s (%d seats)\n", play.getName(), format.format(thisAmount / 100), performance.getAudience()));
            totalAmount += thisAmount;
        }
        stringBuilder.append(String.format("Amount owed is %s\n", format.format(totalAmount / 100)));
        stringBuilder.append(String.format("You earned %s credits\n", volumeCredits));
        return stringBuilder.toString();
    }

    private int getTragedyCredits(Performance performance) {
        return Math.max(performance.getAudience() - 30, 0);
    }

    private double getComedyCredits(Performance performance) {
        double max = getTragedyCredits(performance);
        double floor = Math.floor(performance.getAudience() / 5);
        return max + floor;
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
