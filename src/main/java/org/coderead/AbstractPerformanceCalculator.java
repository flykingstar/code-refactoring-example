package org.coderead;

import org.coderead.model.Performance;

/**
 * @author dengzhe
 * @date 2021/4/10
 */
public abstract class AbstractPerformanceCalculator {
    public abstract double getVolumeCredits(Performance performance);

    public abstract int getAmount(Performance performance);

    public static AbstractPerformanceCalculator of (String type){
        if ("tragedy".equals(type)) {
            return new TragedyCalcultor();
        }
        if ("comedy".equals(type)) {
            return new ComedyCalculator();
        }
        if ("action".equals(type)) {
            return new ActionCalculator();
        }
        throw new IllegalArgumentException("wrong type");
    }
}
