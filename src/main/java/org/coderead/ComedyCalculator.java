package org.coderead;

import org.coderead.model.Performance;

public class ComedyCalculator {
    public ComedyCalculator() {
    }

    double getVolumeCredits(Performance performance) {
        int max = Math.max(performance.getAudience() - 30, 0);
        double floor = Math.floor(performance.getAudience() / 5);
        return floor + max;
    }

    double getAmount(Performance performance) {
        double thisAmount = 30000;
        if (performance.getAudience() > 20) {
            thisAmount += 10000 + 500 *(performance.getAudience() - 20);
        }
        thisAmount += 300 * performance.getAudience();
        return thisAmount;
    }
}