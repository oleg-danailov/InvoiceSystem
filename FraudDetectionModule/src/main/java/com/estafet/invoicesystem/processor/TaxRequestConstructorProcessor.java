package com.estafet.invoicesystem.processor;

import com.estafet.invoicesystem.jpa.model.Invoice;
import com.estafet.invoicesystem.jpa.model.TaxRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by estafet on 20/10/15.
 */
public class TaxRequestConstructorProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Invoice invoice = exchange.getIn().getBody(Invoice.class);

        TaxRequest taxRequest = new TaxRequest();
        taxRequest.setInvoiceType(invoice.getInvoiceType());

        exchange.getOut().setHeader("SOAPAction","http://taxservice.estafet.com/taxRequest");
        exchange.getOut().setHeader("operationName","taxRequest");
        exchange.getOut().setHeader("operationNamespace","http://taxservice.estafet.com/");
        exchange.getOut().setBody(taxRequest);
    }
}
