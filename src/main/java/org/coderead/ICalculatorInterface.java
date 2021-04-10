package org.coderead;

import org.coderead.model.Performance;

/**
 * @author dengzhe
 * @date 2021/4/10
 */
public interface ICalculatorInterface {
    double getVolumeCredits(Performance performance);

    int getAmount(Performance performance);
}
