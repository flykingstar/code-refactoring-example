package org.coderead.model;

import org.coderead.Statement;
import org.coderead.calculator.AbstractCalculator;

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

    public double getVolumeCredits(Map<String, Play> plays, Performance performance) {
        Play play = plays.get(performance.getPlayId());
        return AbstractCalculator.of(play.getType()).getVolumeCredits(performance);
    }

    public int getThisAmount(Performance performance1, Map<String, Play> plays) {
        Play play = plays.get(performance1.getPlayId());
        return AbstractCalculator.of(play.getType()).getAmount(performance1);
    }
}
