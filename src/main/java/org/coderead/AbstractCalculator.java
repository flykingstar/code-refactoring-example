package org.coderead;

import org.coderead.model.Performance;
import org.coderead.model.Play;

/**
 * @author dengzhe
 * @date 2021/4/20
 */
public abstract class AbstractCalculator {
    public abstract double getCredits(Performance performance);

    public abstract double getAmount(Performance performance);

    public static AbstractCalculator getInterfaceByType(Play play) {
        AbstractCalculator calculatorInterface = null;
        try {
            calculatorInterface = (AbstractCalculator) Class.forName(getClassNameByType(play.getType()))
                    .getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("unknown type:" + play.getType());
        }
        return calculatorInterface;
    }


    private static String getClassNameByType(String type) {
        return getPackageNameString() + "." + getTypeString(type) + "Calculator";
    }
    private static String getTypeString(String type) {
        return type.substring(0, 1).toUpperCase() + type.substring(1);
    }

    

    private static String getPackageNameString() {
        return AbstractCalculator.class.getPackage().getName();
    }

}
