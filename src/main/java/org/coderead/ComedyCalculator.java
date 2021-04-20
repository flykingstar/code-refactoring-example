package org.coderead;

import org.coderead.model.Performance;

public class ComedyCalculator implements ICalculatorInterface {
    public ComedyCalculator() {
    }

    @Override
    public double getCredits(Performance performance) {
        return Math.max(performance.getAudience() - 30, 0) + Math.floor(performance.getAudience() / 5);
    }

    @Override
    public double getAmount(Performance performance) {
        double thisAmount;
        thisAmount = 30000;
        if (performance.getAudience() > 20) {
            thisAmount += 10000 + 500 * (performance.getAudience() - 20);
        }
        thisAmount += 300 * performance.getAudience();
        return thisAmount;
    }
}