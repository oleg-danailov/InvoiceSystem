package com.estafet.invoiceservice.processor;

import com.estafet.invoicesystem.jpa.model.Invoice;
import com.estafet.invoicesystem.jpa.model.Tax;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by estafet on 20/10/15.
 */
public class TaxRequestConstructorProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Invoice invoice = exchange.getIn().getBody(Invoice.class);

        Tax taxRequest = new Tax();
        taxRequest.setInvoiceType(invoice.getInvoiceType());

        exchange.getOut().setBody(taxRequest);
    }
}
