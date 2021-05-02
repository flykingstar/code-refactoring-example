package org.coderead;

import com.sun.xml.internal.ws.util.StringUtils;

/**
 * @author dengzhe
 * @date 2021/5/2
 */
public class CalculatorFactory {

    public static ICalculator getInterfaceByType(String type) {
        try {
            return (ICalculator) Class.forName("org.coderead" + "." + getClassName(type) + "Calculator").getConstructor().newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("unknown type:" + type);
        }
    }

    public static String getClassName(String type) {
        return StringUtils.capitalize(type);
    }
}
