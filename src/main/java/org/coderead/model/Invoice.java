package org.coderead.model;

import org.coderead.CalculatorAbstract;
import org.coderead.Statement;

import java.text.NumberFormat;
import java.util.List;
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

    public String formatAmount(NumberFormat format, int thisAmount) {
        return format.format(thisAmount / 100);
    }

    public void formatThisAmount(StringBuilder stringBuilder, NumberFormat format, Map<String, Play> plays) {
        for (Performance performance : getPerformances()) {
            int thisAmount = getCalculatorByType(plays.get(performance.getPlayId())).getAmount(performance);
            stringBuilder.append(String.format(" %s: %s (%d seats)\n", plays.get(performance.getPlayId()).getName(), formatAmount(format, thisAmount), performance.getAudience()));
        }
    }


    public void formatVolumeCredits(StringBuilder stringBuilder, Map<String, Play> plays) {
        int volumeCredits = 0;
        for (Performance performance : getPerformances()) {
            volumeCredits += getCalculatorByType(plays.get(performance.getPlayId())).getVolumeCredits(performance);
        }
        stringBuilder.append(String.format("You earned %s credits\n", volumeCredits));
    }

    public void formatTotalAmount(StringBuilder stringBuilder, NumberFormat format, Map<String, Play> plays, Statement statement) {
        int totalAmount = 0;
        for (Performance performance : getPerformances()) {
            CalculatorAbstract calculator = getCalculatorByType(plays.get(performance.getPlayId()));
            totalAmount += calculator.getAmount(performance);
        }
        stringBuilder.append(String.format("Amount owed is %s\n", formatAmount(format, totalAmount)));
    }

    private CalculatorAbstract getCalculatorByType(Play play) {
        return CalculatorAbstract.getCalculatorByType(play);
    }
}
