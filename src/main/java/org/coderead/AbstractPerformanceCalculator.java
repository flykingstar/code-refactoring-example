package org.coderead;

import org.coderead.model.Performance;

/**
 * @author dengzhe
 * @date 2021/4/10
 */
public abstract class AbstractPerformanceCalculator {
    public abstract double getVolumeCredits(Performance performance);

    public abstract int getAmount(Performance performance);

    public static AbstractPerformanceCalculator of(String type) {
        AbstractPerformanceCalculator calculatorInterface = null;
        if ("tragedy".equals(type)) {
            calculatorInterface = new TragedyCalcultor();
        }

        if ("comedy".equals(type)) {
            calculatorInterface = new ComedyCalculator();
        }
        return calculatorInterface;
    }
}
