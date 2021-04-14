package org.coderead.model;

import org.coderead.CalculatorInterface;
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

    public double getThisAmount(Performance performance, String type) {
        return CalculatorInterface.getCalculatorInterface(type).getAmount(performance);
    }

    public double getAmount(Map<String, Play> plays, Performance performance) {
        double temp;
        Play play = plays.get(performance.getPlayId());
        temp = getThisAmount(performance, play.getType());
        return temp;
    }
}
