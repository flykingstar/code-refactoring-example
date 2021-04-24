package org.coderead;

import org.coderead.model.Invoice;
import org.coderead.model.Performance;
import org.coderead.model.Play;

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
        int volumeCredits = 0;
        String result = String.format("Statement for %s", invoice.getCustomer());
        StringBuilder stringBuilder = new StringBuilder(result);

        Locale locale = new Locale("en", "US");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayId());
            int thisAmount = 0;
            switch (play.getType()) {
                case "tragedy":
                    thisAmount = tragedyCalculator.getTragedyAmount(performance);
                    volumeCredits += tragedyCalculator.getTragedyVolumeCredits(performance);
                    break;
                case "comedy":
                    thisAmount = comedyCalculator.getComedyAmount(performance);
                    volumeCredits += comedyCalculator.getComedyVolumeCredits(performance);
                    break;
                default:
                    throw new RuntimeException("unknown type:" + play.getType());
            }

            stringBuilder.append(String.format(" %s: %s (%d seats)\n", play.getName(), format.format(thisAmount/100), performance.getAudience()));
            totalAmount += thisAmount;
        }
        stringBuilder.append(String.format("Amount owed is %s\n", format.format(totalAmount/100)));
        stringBuilder.append(String.format("You earned %s credits\n", volumeCredits));
        return stringBuilder.toString();
    }

}
