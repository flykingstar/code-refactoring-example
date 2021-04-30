package org.coderead;

import org.coderead.model.Performance;

/**
 * @author dengzhe
 * @date 2021/4/30
 */
public interface ICalculator {
    double getCredits(Performance performance);

    int getAmount(Performance performance);
}
