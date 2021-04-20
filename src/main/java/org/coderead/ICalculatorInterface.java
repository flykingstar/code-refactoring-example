package org.coderead;

import org.coderead.model.Performance;

/**
 * @author dengzhe
 * @date 2021/4/20
 */
public interface ICalculatorInterface {
    double getCredits(Performance performance);

    double getAmount(Performance performance);
}
