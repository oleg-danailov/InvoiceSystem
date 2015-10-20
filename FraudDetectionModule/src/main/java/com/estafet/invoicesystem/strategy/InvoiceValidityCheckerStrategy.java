package com.estafet.invoicesystem.strategy;

import com.estafet.invoicesystem.jpa.model.Invoice;
import com.estafet.invoicesystem.jpa.model.InvoiceResponse;
import com.estafet.invoicesystem.jpa.model.TaxResponse;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.math.BigDecimal;

/**
 * Created by estafet on 20/10/15.
 */
public class InvoiceValidityCheckerStrategy implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange original, Exchange resource) {

        Invoice originalBody = original.getIn().getBody(Invoice.class);
        TaxResponse taxResponse = resource.getIn().getBody(TaxResponse.class);

        BigDecimal amount = originalBody.getInvoiceAmount();

        BigDecimal taxPercent = taxResponse.getTaxPercent();
        BigDecimal taxesAmount = amount.multiply((taxPercent == null ? new BigDecimal(0.18) : taxPercent));

        if (originalBody.getTotalAmount().equals(taxesAmount.add(amount))) {
            originalBody.setInvoiceStatus("checked");
        } else {
            originalBody.setInvoiceStatus("error");
        }

        if (original.getPattern().isOutCapable()) {
            original.getOut().setBody(originalBody);
            original.getOut().setHeader("enriched", "true");
        } else {
            original.getIn().setBody(originalBody);
            original.getIn().setHeader("enriched", "true");
        }
        return original;
    }
}