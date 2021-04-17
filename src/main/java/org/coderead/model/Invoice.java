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

    public int getThisAmount(Performance performance, Map<String, Play> plays) {
        Play play = plays.get(performance.getPlayId());
        return AbstractCalculator.of(play.getType()).getAmount(performance);
    }

    public void formatVolumeCredits(StringBuilder stringBuilder, Map<String, Play> plays) {
        double volumeCredits = performances.stream()
                .mapToDouble(performance -> AbstractCalculator.of(plays.get(performance.getPlayId()).getType())
                        .getVolumeCredits(performance)).sum();
        stringBuilder.append(String.format("You earned %s credits\n", volumeCredits));
    }

    public void formatThisAmount(StringBuilder stringBuilder, Map<String, Play> plays) {
        performances.forEach(performance -> {
            Play play = plays.get(performance.getPlayId());
            stringBuilder.append(String.format(" %s: %s (%d seats)\n", play.getName(),
                    formatUSD(AbstractCalculator.of(play.getType()).getAmount(performance)), performance.getAudience()));
        });
    }

    public void formatTotalAmount(StringBuilder stringBuilder, Map<String, Play> plays) {
        int totalAmount = performances.stream().mapToInt(performance -> getThisAmount(performance, plays)).sum();
        stringBuilder.append(String.format("Amount owed is %s\n", formatUSD(totalAmount)));
    }

    public String formatUSD(int amount) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(amount / 100);
    }
}
