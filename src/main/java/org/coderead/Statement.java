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
            tempCredits = tragedyCalculator.getVolumeCredits(performance);
        }
        if ("comedy".equals(play.getType())) {
            tempCredits = comedyCalculator.getVolumeCredits(performance);
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
                thisAmount = tragedyCalculator.getAmount(performance);
                break;
            case "comedy":
                thisAmount = comedyCalculator.getAmount(performance);
                break;
            default:
                throw new RuntimeException("unknown type:" + play.getType());
        }
        return thisAmount;
    }


}
