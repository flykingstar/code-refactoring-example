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

    private final Invoice invoice;
    private final Map<String, Play> plays;

    public Statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String show() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Statement for %s", invoice.getCustomer()));
        Locale locale = new Locale("en", "US");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        dealThisAmount(stringBuilder, format);
        dealTotalAmount(stringBuilder, locale);
        dealCredits(stringBuilder);
        return stringBuilder.toString();
    }

    private void dealCredits(StringBuilder stringBuilder) {
        double volumeCredits;
        volumeCredits = invoice.getPerformances().stream().mapToDouble(perfomance -> invoice.getCredits(perfomance,plays.get(perfomance.getPlayId()))).sum();
        stringBuilder.append(formatCredits(volumeCredits));
    }

    private void dealTotalAmount(StringBuilder stringBuilder, Locale locale) {
        int totalAmount = invoice.getPerformances().stream().mapToInt(performance1 -> {
            Play play = plays.get(performance1.getPlayId());
            return invoice.getThisAmount(performance1, play.getType());
        }).sum();
        stringBuilder.append(formatTotalAmount(totalAmount, locale));
    }

    private void dealThisAmount(StringBuilder stringBuilder, NumberFormat format) {
        invoice.getPerformances().forEach(performance -> {
            Play play = plays.get(performance.getPlayId());
            int thisAmount = invoice.getThisAmount(performance, play.getType());
            formatThisAmount(stringBuilder, format, performance, play, thisAmount);
        });
    }

    private StringBuilder formatThisAmount(StringBuilder stringBuilder, NumberFormat format, Performance performance, Play play, int thisAmount) {
        return stringBuilder.append(String.format(" %s: %s (%d seats)" + "\n", play.getName(), format.format(thisAmount / 100), performance.getAudience()));
    }

    private String formatTotalAmount(int totalAmount, Locale locale) {
        return "Amount owed is " + NumberFormat.getCurrencyInstance(locale).format(totalAmount / 100) + "\n";
    }

    private String formatCredits(double volumeCredits) {
        return "You earned " + volumeCredits + " credits\n";
    }


}
