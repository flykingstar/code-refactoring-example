package org.coderead;

import org.coderead.calculator.AbstractCalculator;
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

    private Invoice invoice;
    private Map<String, Play> plays;

    public Statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String show() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Statement for %s", invoice.getCustomer()));
        formatThisAmount(stringBuilder);
        formatTotalAmount(stringBuilder);
        formatCredits(stringBuilder);
        return stringBuilder.toString();
    }

    private void formatCredits(StringBuilder stringBuilder) {
        double volumeCredits = 0;

        for (Performance performance : invoice.getPerformances()) {
            volumeCredits += invoice.getVolumeCredits(plays, performance);
        }
        stringBuilder.append(String.format("You earned %s credits\n", volumeCredits));
    }

    private void formatTotalAmount(StringBuilder stringBuilder) {
        int totalAmount = 0;
        for (Performance performance1 : invoice.getPerformances()) {
            totalAmount += invoice.getThisAmount(performance1,plays);
        }
        stringBuilder.append(String.format("Amount owed is %s\n", formatUSD(totalAmount)));
    }

    private void formatThisAmount(StringBuilder stringBuilder) {
        for (Performance performance2 : invoice.getPerformances()) {
            Play play = plays.get(performance2.getPlayId());
            stringBuilder.append(String.format(" %s: %s (%d seats)\n", play.getName(),
                    formatUSD(AbstractCalculator.of(play.getType()).getAmount(performance2)), performance2.getAudience()));
        }
    }

    private String formatUSD(int thisAmount) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(thisAmount / 100);
    }


}
