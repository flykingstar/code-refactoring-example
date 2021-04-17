package org.coderead.calculator;

import org.coderead.model.Performance;

/**
 * @author dengzhe
 * @date 2021/4/17
 */
public abstract class AbstractCalculator {
    public abstract double getVolumeCredits(Performance performance);

    public abstract int getAmount(Performance performance);

    public static AbstractCalculator of(String type) {
        try {
            return (AbstractCalculator) Class.forName(getPackageName() + "." +
                    formatTypeName(type) + "Calculator").getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(String.format("type name [%s] is wrong",type));
        }
    }

    private static String formatTypeName(String type) {
        return type.substring(0, 1).toUpperCase() + type.substring(1);
    }

    private static String getPackageName() {
        return AbstractCalculator.class.getPackage().getName();
    }
}
