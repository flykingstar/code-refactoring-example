package org.coderead;

import org.coderead.model.Performance;

public class ComedyCalculator implements CalculatorInterface {
    public ComedyCalculator() {
    }

    @Override
    public double getVolumeCredits(Performance performance) {
        return Math.floor(performance.getAudience() / 5) + Math.max(performance.getAudience() - 30, 0);
    }

    @Override
    public double getAmount(Performance performance) {
        double thisAmount = 30000;
        if (performance.getAudience() > 20) {
            thisAmount += 10000 + 500 *(performance.getAudience() - 20);
        }
        thisAmount += 300 * performance.getAudience();
        return thisAmount;
    }
}