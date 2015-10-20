package com.estafet.invoicesystem.processor;

import com.estafet.invoicesystem.jpa.model.InvoiceResponse;
import com.estafet.invoicesystem.jpa.model.Tax;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by estafet on 20/10/15.
 */
public class TaxRequestConstructorProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        InvoiceResponse invoice = exchange.getIn().getBody(InvoiceResponse.class);

        Tax taxRequest = new Tax();
        taxRequest.setInvoiceType(invoice.getInvoiceType());

        exchange.getOut().setBody(taxRequest);
    }
}
