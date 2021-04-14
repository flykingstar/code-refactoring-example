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
            return tragedyCalculator.getVolumeCredits(performance);
        }
        if ("comedy".equals(type)) {
            return comedyCalculator.getVolumeCredits(performance);
        }
        return 0;
    }

    private double getThisAmount(Performance performance, String type) {
        double thisAmount;
        switch (type) {
            case "tragedy":
                thisAmount = tragedyCalculator.getAmount(performance);
                break;
            case "comedy":
                thisAmount = comedyCalculator.getAmount(performance);
                break;
            default:
                throw new RuntimeException("unknown type:" + type);
        }
        return thisAmount;
    }

}
