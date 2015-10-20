package com.estafet.invoiceservice.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

/**
 * Created by estafet on 20/10/15.
 */
public class GetInvoiceServiceRoute extends RouteBuilder{
    @Override
    public void configure() throws Exception {
        JaxbDataFormat jxb = new  JaxbDataFormat("com.estafet.invoicesystem.jpa.model");

        from("cxf:bean:getInvoiceRequest").streamCaching().unmarshal(jxb).beanRef("getInvoiceProcessor", "getInvoice")
                .log("GetInvoice Request: ${body}").marshal(jxb).to("mock:result");
    }
}
