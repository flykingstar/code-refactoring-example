package org.coderead;

import org.coderead.model.Invoice;
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
        invoice.formatThisAmount(stringBuilder, NumberFormat.getCurrencyInstance(new Locale("en", "US")), plays);
        invoice.formatTotalAmount(stringBuilder, NumberFormat.getCurrencyInstance(new Locale("en", "US")), plays, this);
        invoice.formatVolumeCredits(stringBuilder, plays);
        return stringBuilder.toString();
    }

}
