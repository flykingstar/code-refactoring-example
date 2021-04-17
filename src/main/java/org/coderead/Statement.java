package org.coderead;

import org.coderead.model.Invoice;
import org.coderead.model.Play;
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
        StringBuilder stringBuilder = initStringBuilder();
        invoice.formatThisAmount(stringBuilder, plays);
        invoice.formatTotalAmount(stringBuilder, plays);
        invoice.formatVolumeCredits(stringBuilder, plays);
        return stringBuilder.toString();
    }

    private StringBuilder initStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Statement for %s", invoice.getCustomer()));
        return stringBuilder;
    }


}
