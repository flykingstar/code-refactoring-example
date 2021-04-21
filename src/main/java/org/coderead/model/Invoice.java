package org.coderead.model;

import org.coderead.AbstractCalculator;

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

    public void formatVolumeCredits(StringBuilder stringBuilder, Map<String, Play> playMap) {
        double creditsResult = 0;
        for (Performance performance1 : getPerformances()) {
            Play play1 = playMap.get(performance1.getPlayId());
            double tempVolumeCredits = 0;
            AbstractCalculator calculatorInterface = AbstractCalculator.getInterfaceByType(play1);
            tempVolumeCredits += calculatorInterface.getCredits(performance1);
            creditsResult += tempVolumeCredits;
        }
        stringBuilder.append(String.format("You earned %s credits\n", creditsResult));
    }

    public void formatTotalAmount(StringBuilder stringBuilder, Map<String, Play> plays) {
        NumberFormat format = getFormat();
        int tempAmount = 0;
        for (Performance performance : getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            tempAmount += formatThisAmount(performance, play);
        }
        stringBuilder.append(String.format("Amount owed is %s\n", formatUSD(format, tempAmount)));
    }

    public double formatThisAmount(Performance performance, Play play) {
        AbstractCalculator calculatorInterface = AbstractCalculator.getInterfaceByType(play);
        return calculatorInterface.getAmount(performance);
    }

    public void formatThisAmount(StringBuilder stringBuilder, Map<String, Play> tempPlays) {
        NumberFormat format = getFormat();
        for (Performance performance : getPerformances()) {
            Play play = tempPlays.get(performance.getPlayId());
            stringBuilder.append(String.format(" %s: %s (%d seats)\n", play.getName(), formatUSD(format, formatThisAmount(performance, play)), performance.getAudience()));
        }
    }

    private NumberFormat getFormat() {
        return NumberFormat.getCurrencyInstance(new Locale("en", "US"));
    }

    public String formatUSD(NumberFormat format, double thisAmount) {
        return format.format(thisAmount / 100);
    }

}
