package com.estafet.invoicesystem.route;

import com.estafet.invoicesystem.processor.TaxRequestConstructorProcessor;
import com.estafet.invoicesystem.strategy.InvoiceValidityCheckerStrategy;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

/**
 * Created by Yordan Stankov on 02/10/15.
 */
public class FraudDetectionRouteBuilder extends RouteBuilder {

    private InvoiceValidityCheckerStrategy invoiceValidityCheckerStrategy;

    public void setInvoiceValidityCheckerStrategy(InvoiceValidityCheckerStrategy invoiceValidityCheckerStrategy) {
        this.invoiceValidityCheckerStrategy = invoiceValidityCheckerStrategy;
    }

    @Override
    public void configure() throws Exception {

        JaxbDataFormat jxb = new  JaxbDataFormat("com.estafet.invoicesystem.jpa.model");

        from("activemq:incomingInvoices").delayer(1000)
                .routeId("fraud_detection_route")
                .streamCaching()
                .unmarshal(jxb)
                .log(LoggingLevel.INFO, "Received message with body: ${body}")
                .enrich("direct:call_tax", invoiceValidityCheckerStrategy).id("enricher")
                .beanRef("fraudDetectionProcessor", "updateInvoiceStatus").id("updater");

        //route for retrieval taxResponse by type
        from("direct:call_tax").routeId("call_tax_route").streamCaching().process(new TaxRequestConstructorProcessor())
                .log("Before call to Tax Request: ${body} ").marshal(jxb).to("cxf:bean:taxRequest").end();


    }
}
