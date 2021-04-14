package org.coderead;

import org.coderead.model.Performance;

/**
 * @author dengzhe
 * @date 2021/4/14
 */
public interface CalculatorInteface {
    double getVolumeCredits(Performance performance);

    double getAmount(Performance performance);
}
