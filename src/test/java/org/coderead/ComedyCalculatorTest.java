package org.coderead;

import org.coderead.model.Performance;
import org.junit.Test;



/**
 * @author dengzhe
 * @date 2021/4/11
 */
public class ComedyCalculatorTest {

    @Test
    public void getVolumeCredits() {
        ComedyCalculator calculator = new ComedyCalculator();
        Performance performance = new Performance();
        performance.setAudience(35);
        System.out.println(calculator.getVolumeCredits(performance));
    }
}