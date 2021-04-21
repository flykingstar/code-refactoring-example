package org.coderead;

import org.coderead.model.Performance;

public class TragedyCalculator extends AbstractCalculator {
    public TragedyCalculator() {
    }

    @Override
    public double getCredits(Performance performance) {
        return Math.max(performance.getAudience() - 30, 0);
    }

    @Override
    public double getAmount(Performance performance) {
        double thisAmount;
        thisAmount = 40000;
        if (performance.getAudience() > 30) {
            thisAmount += 1000 * (performance.getAudience() - 30);
        }
        return thisAmount;
    }
}