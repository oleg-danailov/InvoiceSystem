package com.estafet.invoicesystem.strategy;

import com.estafet.invoicesystem.jpa.dao.api.InvoiceDAO;
import com.estafet.invoicesystem.jpa.model.Invoice;
import com.estafet.invoicesystem.jpa.model.TaxResponse;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by estafet on 20/10/15.
 */
public class InvoiceValidityCheckerStrategy implements AggregationStrategy {

    private InvoiceDAO invoiceDAO;
    public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @Override
    public Exchange aggregate(Exchange original, Exchange resource) {

        Invoice originalBody = original.getIn().getBody(Invoice.class);

        if (originalBody.getInvoiceId() == null) {
            originalBody = invoiceDAO.findByNumberAndProvider(originalBody.getInvoiceNumber(), originalBody.getProviderCompany().getCompanyName());
        } else {
            originalBody = invoiceDAO.getInvoice(originalBody.getInvoiceId());
        }

        TaxResponse taxResponse = resource.getIn().getBody(TaxResponse.class);

        BigDecimal amount = originalBody.getInvoiceAmount();

        BigDecimal taxPercent = taxResponse.getTaxPercent();
        BigDecimal taxesAmount = amount.multiply((taxPercent == null ? new BigDecimal(0.18) : taxPercent));
        MathContext roundContext = new MathContext(2, RoundingMode.HALF_EVEN);

        if (originalBody.getTotalAmount().round(roundContext).equals(taxesAmount.add(amount).round(roundContext))) {
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
