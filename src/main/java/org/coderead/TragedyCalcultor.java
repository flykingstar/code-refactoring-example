package org.coderead;

import org.coderead.model.Performance;

public class TragedyCalcultor extends AbstractPerformanceCalculator {
    public TragedyCalcultor() {
    }

    @Override
    public double getVolumeCredits(Performance performance) {
        return Math.max(performance.getAudience() - new Double(30), 0);
    }

    @Override
    public int getAmount(Performance performance) {
        int thisAmount;
        thisAmount = 40000;
        if (performance.getAudience() > 30) {
            thisAmount += 1000 * (performance.getAudience() - 30);
        }
        return thisAmount;
    }
}