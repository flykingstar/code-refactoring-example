package org.coderead;

import com.sun.xml.internal.ws.util.StringUtils;
import org.coderead.model.Performance;
import org.coderead.model.Play;

/**
 * @author dengzhe
 * @date 2021/4/24
 */
public abstract class CalculatorAbstract {
    public abstract double getVolumeCredits(Performance performance);

    public abstract int getAmount(Performance performance);

    public static CalculatorAbstract getCalculatorByType(Play play) {
        try {
            String calculatorClassName = getCalculatorClassName(play.getType());
            return (CalculatorAbstract) Class.forName(calculatorClassName).getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("unknown type:" + play.getType());
        }
    }

    private static String getCalculatorClassName(String type) {
        return getPackageName() + "." + StringUtils.capitalize(type) + "Calculator";
    }

    private static String getPackageName() {
        return CalculatorAbstract.class.getPackage().getName();
    }
}
