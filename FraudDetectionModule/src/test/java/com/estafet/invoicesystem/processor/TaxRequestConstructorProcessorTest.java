package com.estafet.invoicesystem.processor;

import com.estafet.invoicesystem.jpa.model.Invoice;
import com.estafet.invoicesystem.jpa.model.TaxRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Test;


import static org.junit.Assert.*;

public class TaxRequestConstructorProcessorTest {

    @Test
    public void testProcess() throws Exception {
        TaxRequestConstructorProcessor processor = new TaxRequestConstructorProcessor();

        Invoice invoice = new Invoice();
        invoice.setInvoiceType("VAT");

        Exchange exchange = new DefaultExchange(new DefaultCamelContext());

        exchange.getIn().setBody(invoice);
        processor.process(exchange);
        Message out = exchange.getOut();
        TaxRequest taxRequest = out.getBody(TaxRequest.class);

        assertEquals("VAT", taxRequest.getInvoiceType());
        assertEquals("http://taxservice.estafet.com/taxRequest", out.getHeader("SOAPAction"));
        assertEquals("taxRequest", out.getHeader("operationName"));
        assertEquals("http://taxservice.estafet.com/", out.getHeader("operationNamespace"));

    }
}