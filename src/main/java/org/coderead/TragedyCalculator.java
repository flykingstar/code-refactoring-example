package org.coderead;

import org.coderead.model.Performance;

public class TragedyCalculator {
    public TragedyCalculator() {
    }

    double getVolumeCredits(Performance performance) {
        return Math.max(performance.getAudience() - 30, 0);
    }

    double getAmount(Performance performance) {
        double thisAmount = 40000;
        if (performance.getAudience() > 30) {
            thisAmount += 1000 * (performance.getAudience() - 30);
        }
        return thisAmount;
    }
}