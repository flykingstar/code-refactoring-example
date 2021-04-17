package org.coderead;

import org.coderead.model.Performance;

/**
 * @author dengzhe
 * @date 2021/4/17
 */
public interface ICalculator {
    double getVolumeCredits(Performance performance);

    int getAmount(Performance performance);
}
