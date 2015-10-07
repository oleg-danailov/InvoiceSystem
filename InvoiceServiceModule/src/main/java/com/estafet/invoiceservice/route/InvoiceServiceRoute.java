package com.estafet.invoiceservice.route;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Angelo Atanasov on 07/10/15.
 */
public class InvoiceServiceRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // webservice responses
        from("cxf:bean:invoiceRequest")
                .id("invoice_service_route")
                .log("${body}")
                .process(new Processor() {

                })
                .to("mock:result");
    }
}
