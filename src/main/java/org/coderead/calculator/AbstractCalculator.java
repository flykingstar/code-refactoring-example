package org.coderead.calculator;

import com.sun.xml.internal.ws.util.StringUtils;
import org.coderead.model.Performance;

/**
 * @author dengzhe
 * @date 2021/4/25
 */
public abstract class AbstractCalculator {
    public abstract int getAmount(Performance performance);

    public abstract double getVolumeCredits(Performance performance);

    public static AbstractCalculator getCalculatorByType(String playType) {
        try {
            return (AbstractCalculator) Class.forName(getPackageName() + "." + StringUtils.capitalize(playType) + "Calculator")
                    .getConstructor().newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("unknown type:" + playType);
        }
    }

    private static String getPackageName() {
        return AbstractCalculator.class.getPackage().getName();
    }
}
