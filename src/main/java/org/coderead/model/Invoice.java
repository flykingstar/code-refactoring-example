package org.coderead.model;

import org.coderead.AbstractPerformanceCalculator;

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

    public double getVolumeCredits(Map<String, Play> plays) {
        double volumeCredits = 0;
        for (Performance performance : getPerformances()) {
            volumeCredits += AbstractPerformanceCalculator.of(plays.get(performance.getPlayId()).getType()).getVolumeCredits(performance);
        }
        return volumeCredits;
    }
}
