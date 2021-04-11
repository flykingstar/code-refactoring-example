package org.coderead;

import org.coderead.model.Performance;

/**
 * @author dengzhe
 * @date 2021/4/10
 */
public abstract class AbstractPerformanceCalculator {
    public abstract double getVolumeCredits(Performance performance);

    public abstract int getAmount(Performance performance);

    public static AbstractPerformanceCalculator of(String type) {
        try {
            return (AbstractPerformanceCalculator) Class.forName(getStringName(type)).newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("wrong type");
        }
    }

    private static String getStringName(String type) {
        String result = getPackageName() + "." + captureName(type);
        return result;
    }

    private static String getPackageName() {
        return AbstractPerformanceCalculator.class.getPackage().getName();
    }

    private static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs) + "Calculator";
    }
}
