package org.coderead.model;

import org.coderead.Statement;

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

    public double getVolumeCredits(Map<String, Play> plays, Performance performance, Statement statement) {
        Play play = plays.get(performance.getPlayId());
        return statement.getVolumeCredits(performance, play);
    }

    public int getThisAmount(Performance performance1, Map<String, Play> plays, Statement statement) {
        Play play = plays.get(performance1.getPlayId());
        return statement.getThisAmount(performance1, play.getType());
    }
}
