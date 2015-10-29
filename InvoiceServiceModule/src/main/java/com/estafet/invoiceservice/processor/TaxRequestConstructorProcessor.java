package com.estafet.invoiceservice.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by estafet on 20/10/15.
 */
public class TaxRequestConstructorProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setHeader("SOAPAction","http://taxservice.estafet.com/taxRequest");
        exchange.getIn().setHeader("operationName","taxRequest");
        exchange.getIn().setHeader("operationNamespace","http://taxservice.estafet.com/");
    }
}
