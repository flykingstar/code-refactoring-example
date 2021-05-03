package org.coderead;

import org.coderead.model.Performance;

/**
 * @author dengzhe
 * @date 2021/5/3
 */
public interface ICalculator {
    double getCredits(Performance performance);

    int getAmount(Performance performance);
}
