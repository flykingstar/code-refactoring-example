package org.coderead;

import org.coderead.model.Performance;

/**
 * @author dengzhe
 * @date 2021/4/14
 */
public interface CalculatorInterface {
    double getVolumeCredits(Performance performance);

    double getAmount(Performance performance);

    static CalculatorInterface getCalculatorInterface(String type) {
        try {
            return (CalculatorInterface) Class.forName(getClassName(type)).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("unknown type:" + type);
        }
    }

    static String getClassName(String type) {
        return getCurrentPackageName() + "." + type.substring(0, 1).toUpperCase() + type.substring(1) + "Calculator";
    }

    static String getCurrentPackageName() {
        return CalculatorInterface.class.getPackage().getName();
    }
}
