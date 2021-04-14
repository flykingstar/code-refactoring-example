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
        formatPerformance(stringBuilder);
        formatTotalAmount(stringBuilder);
        formatVolumeCredits(stringBuilder);
        return stringBuilder.toString();
    }

    private void formatVolumeCredits(StringBuilder stringBuilder) {
        double volumeCredits = 0;
        for (Performance performance : invoice.getPerformances()) {
            volumeCredits += getVolumeCredits(performance, plays.get(performance.getPlayId()).getType());
        }
        stringBuilder.append(String.format("You earned %s credits\n", volumeCredits));
    }

    private void formatTotalAmount(StringBuilder stringBuilder) {
        double totalAmount = 0;
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            totalAmount += getThisAmount(performance, play.getType());
        }
        stringBuilder.append(String.format("Amount owed is %s\n", formatUSD(totalAmount)));
    }

    private void formatPerformance(StringBuilder stringBuilder) {
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            stringBuilder.append(String.format(" %s: %s (%d seats)\n", play.getName(), formatUSD(getThisAmount(performance, play.getType())), performance.getAudience()));
        }
    }

    private String formatUSD(double thisAmount) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(thisAmount / 100);
    }

    private double getVolumeCredits(Performance performance, String type) {
        if("tragedy".equals(type)){
            return getTragedyVolumeCredits(performance);
        }
        if ("comedy".equals(type)) {
            return getComedyVolumeCredits(performance);
        }
        return 0;
    }

    private double getComedyVolumeCredits(Performance performance) {
        int max = Math.max(performance.getAudience() - 30, 0);
        double floor = Math.floor(performance.getAudience() / 5);
        return floor + max;
    }

    private double getTragedyVolumeCredits(Performance performance) {
        return Math.max(performance.getAudience() - 30, 0);
    }

    private double getThisAmount(Performance performance, String type) {
        double thisAmount;
        switch (type) {
            case "tragedy":
                thisAmount = getTragedyAmount(performance);
                break;
            case "comedy":
                thisAmount = getComedyAmount(performance);
                break;
            default:
                throw new RuntimeException("unknown type:" + type);
        }
        return thisAmount;
    }

    private double getComedyAmount(Performance performance) {
        double thisAmount = 30000;
        if (performance.getAudience() > 20) {
            thisAmount += 10000 + 500 *(performance.getAudience() - 20);
        }
        thisAmount += 300 * performance.getAudience();
        return thisAmount;
    }

    private double getTragedyAmount(Performance performance) {
        double thisAmount = 40000;
        if (performance.getAudience() > 30) {
            thisAmount += 1000 * (performance.getAudience() - 30);
        }
        return thisAmount;
    }
}
