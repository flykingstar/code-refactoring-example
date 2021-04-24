package org.coderead;

import org.coderead.model.Performance;

/**
 * @author dengzhe
 * @date 2021/4/24
 */
public interface ICalculator {
    abstract double getVolumeCredits(Performance performance);

    abstract int getAmount(Performance performance);
}
