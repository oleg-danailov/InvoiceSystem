package com.estafet.invoiceservice.route;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

/**
 * Created by Angelo Atanasov on 07/10/15.
 */
public class InvoiceServiceRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // webservice responses

        JaxbDataFormat jxb = new  JaxbDataFormat("com.estafet.invoicesystem.jpa.model");

        from("cxf:bean:invoiceRequest").multicast().to("direct:persist", "direct:incomingInvoices");

        from("direct:persist").id("invoice_service_route_jpa")
                .log("${body}").unmarshal(jxb)
                .beanRef("invoiceProcessor", "persistInvoice");

        from("direct:incomingInvoices").id("invoice_service_route_activemq")
                .log("Before activemq : ${body}")
                .to(ExchangePattern.InOnly, "activemq:incomingInvoices");

    }
}
