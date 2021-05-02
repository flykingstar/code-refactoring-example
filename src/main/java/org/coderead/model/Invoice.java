package org.coderead.model;

import org.coderead.CalculatorFactory;

import java.util.List;

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

    public int getThisAmount(Performance performance, String type) {
        return CalculatorFactory.getInterfaceByType(type).getAmount(performance);
    }

    public double getCredits(Performance performance, Play play) {
        return CalculatorFactory.getInterfaceByType(play.getType()).getCredits(performance);
    }
}
