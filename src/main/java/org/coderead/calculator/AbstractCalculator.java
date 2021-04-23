package org.coderead.calculator;

import com.sun.xml.internal.ws.util.StringUtils;
import org.coderead.model.Performance;

/**
 * @author dengzhe
 * @date 2021/4/23
 */
public abstract class AbstractCalculator {
    public abstract int getAmount(Performance performance);

    public abstract double getVolumeCredits(Performance performance);

    public static AbstractCalculator of(String type) {
        try {
            return (AbstractCalculator) Class.forName(getPackageStr() + "." + formatType(type) + "Calculator").getConstructor().newInstance();
        } catch (Exception ex){
            throw new RuntimeException("unknown type:" + type);
        }
    }
    private static String getPackageStr() {
        return AbstractCalculator.class.getPackage().getName();
    }

    private static String formatType(String type) {
        return StringUtils.capitalize(type);
    }

}
