package org.coderead;

import org.coderead.model.Performance;

public class ActionCalculator implements CalculatorInterface {
    public ActionCalculator() {
    }

    @Override
    public double getVolumeCredits(Performance performance) {
        return Math.max(performance.getAudience() - 30, 0);
    }

    @Override
    public double getAmount(Performance performance) {
        double thisAmount = 40000;
        if (performance.getAudience() > 30) {
            thisAmount += 1000 * (performance.getAudience() - 30);
        }
        return thisAmount;
    }
}