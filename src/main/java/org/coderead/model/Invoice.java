package org.coderead.model;

import org.coderead.calculator.AbstractCalculator;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 发票
 *
 * @author kendoziyu
 * @since 2020/10/11 0011
 */
public class Invoice {

    private String customer;

    private List<Performance> performances;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public String formatUSD(NumberFormat format, int thisAmount) {
        return format.format(thisAmount / 100);
    }

    public void formatThisAmount(StringBuilder stringBuilder, Map<String, Play> tempPlays) {
        for (Performance performance : getPerformances()) {
            AbstractCalculator calculator = AbstractCalculator.of(tempPlays.get(performance.getPlayId()).getType());
            stringBuilder.append(String.format(" %s: %s (%s seats)\n", tempPlays.get(performance.getPlayId()).getName(),
                    formatUSD(getNumberFormat(), calculator.getAmount(performance)), performance.getAudience()));
        }
    }

    public void formatTotalFormat(StringBuilder stringBuilder, Map<String, Play> play) {
        int totalAmount = 0;
        for (Performance performance : getPerformances()) {
            AbstractCalculator calculator = AbstractCalculator.of(play.get(performance.getPlayId()).getType());
            totalAmount += calculator.getAmount(performance);
        }
        stringBuilder.append(String.format("Amount owed is %s\n", formatUSD(getNumberFormat(), totalAmount)));
    }

    public NumberFormat getNumberFormat() {
        return NumberFormat.getCurrencyInstance(new Locale("en", "US"));
    }

    public void formatVolumeCredits(StringBuilder stringBuilder, Map<String, Play> plays) {
        double volumeCredits = 0;
        for (Performance performance : getPerformances()) {
            AbstractCalculator calculator = AbstractCalculator.of(plays.get(performance.getPlayId()).getType());
            volumeCredits += calculator.getVolumeCredits(performance);
        }
        stringBuilder.append(String.format("You earned %s credits\n", volumeCredits));
    }
}
