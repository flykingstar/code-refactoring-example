package org.coderead;

import org.coderead.model.Performance;

public class LoveCalculator extends AbstractPerformanceCalculator {
    public LoveCalculator() {
    }

    @Override
    public double getVolumeCredits(Performance performance) {
        double max = Math.max(performance.getAudience() - 30, 0);
        double floor = Math.floor(performance.getAudience() / 5);
        return max + floor;
    }

    @Override
    public int getAmount(Performance performance) {
        int thisAmount;
        thisAmount = 30000;
        if (performance.getAudience() > 20) {
            thisAmount += 10000 + 500 * (performance.getAudience() - 20);
        }
        thisAmount += 300 * performance.getAudience();
        return thisAmount;
    }
}