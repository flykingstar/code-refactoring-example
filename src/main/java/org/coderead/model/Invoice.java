package org.coderead.model;

import org.coderead.calculator.AbstractCalculator;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void formatThisAmount(StringBuilder stringBuilder, NumberFormat format, Map<String, Play> plays) {
         stringBuilder.append(getPerformances().stream().map(performance->
                 getThisAmountStr(format,plays,performance)).collect(Collectors.joining()));
    }

    private String getThisAmountStr(NumberFormat format, Map<String, Play> plays, Performance performance) {
        return String.format(" %s: %s (%d seats)" + "\n", plays.get(performance.getPlayId()).getName(),
                formatAmount(format,
                        getTotalAmount(performance, plays.get(performance.getPlayId()).getType())),
                performance.getAudience());
    }

    public void formatVolumeCredits(StringBuilder stringBuilder, Map<String, Play> plays) {
        double volumeCredits = getPerformances().stream().mapToDouble(performance -> getVolumeCredits(plays,performance)).sum();
        stringBuilder.append(String.format("You earned %s credits" + "\n", volumeCredits));
    }

    private double getVolumeCredits(Map<String, Play> plays, Performance performance) {
        return AbstractCalculator.getCalculatorByType(plays.get(performance.getPlayId()).getType())
                .getVolumeCredits(performance);
    }

    public void formatTotalAmount(StringBuilder stringBuilder, NumberFormat format, Map<String, Play> plays) {
        int totalAmount = getPerformances().stream().mapToInt(performance->getTotalAmount(performance, plays.get(performance.getPlayId()).getType())).sum();
        stringBuilder.append(String.format("Amount owed is %s" + "\n", formatAmount(format, totalAmount)));
    }

    private int getTotalAmount(Performance performance, String playType) {
        return AbstractCalculator.getCalculatorByType(playType).getAmount(performance);
    }

    private String formatAmount(NumberFormat format, int totalAmount) {
        return format.format(totalAmount / 100);
    }
}
