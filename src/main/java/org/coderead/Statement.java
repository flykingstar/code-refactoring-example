package org.coderead;

import org.coderead.model.Invoice;
import org.coderead.model.Performance;
import org.coderead.model.Play;

import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * 客户服务类
 *
 * @author kendoziyu
 * @since 2020/10/11 0011
 */
public class Statement {

    private final TragedyCalculator tragedyCalculator = new TragedyCalculator();
    private final ComedyCalculator comedyCalculator = new ComedyCalculator();
    private Invoice invoice;
    private Map<String, Play> plays;

    public Statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String show() {
        int totalAmount = 0;
        double volumeCredits = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Statement for %s", invoice.getCustomer()));

        Locale locale = new Locale("en", "US");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            volumeCredits += getVolumeCredits(performance, play);
        }
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            totalAmount += getThisAmount(performance, play);
        }
        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            stringBuilder.append(String.format(" %s: %s (%d seats)\n", play.getName(), formatUSD(format, getThisAmount(performance, play)), performance.getAudience()));
        }
        stringBuilder.append(String.format("Amount owed is %s\n", formatUSD(format, totalAmount)));
        stringBuilder.append(String.format("You earned %s credits\n", volumeCredits));
        return stringBuilder.toString();
    }

    private double getThisAmount(Performance performance, Play play) {
        ICalculatorInterface calculatorInterface = getInterfaceByType(play);
        return calculatorInterface.getAmount(performance);
    }

    private double getVolumeCredits(Performance performance, Play play) {
        double tempVolumeCredits = 0;
        ICalculatorInterface calculatorInterface = getInterfaceByType(play);
        tempVolumeCredits += calculatorInterface.getCredits(performance);
        return tempVolumeCredits;
    }

    private ICalculatorInterface getInterfaceByType(Play play) {
        ICalculatorInterface calculatorInterface = null;
        try {
            calculatorInterface = (ICalculatorInterface) Class.forName(getClassNameByType(play.getType()))
                    .getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("unknown type:" + play.getType());
        }
        return calculatorInterface;
    }

    private String getClassNameByType(String type) {
        return getPackageNameString() + "." + getTypeString(type) + "Calculator";
    }

    private String getTypeString(String type) {
        return type.substring(0, 1).toUpperCase() + type.substring(1);
    }

    private String getPackageNameString() {
        return this.getClass().getPackage().getName();
    }



    private String formatUSD(NumberFormat format, double thisAmount) {
        return format.format(thisAmount / 100);
    }
}
