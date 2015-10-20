package com.estafet.invoicesystem.route;

import com.estafet.invoicesystem.jpa.model.InvoiceResponse;
import com.estafet.invoicesystem.jpa.model.Tax;
import com.estafet.invoicesystem.jpa.model.TaxResponse;
import com.estafet.invoicesystem.processor.TaxRequestConstructorProcessor;
import com.estafet.invoicesystem.strategy.InvoiceValidityCheckerStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.math.BigDecimal;

/**
 * Created by Yordan Stankov on 02/10/15.
 */
public class FraudDetectionRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        JaxbDataFormat jxb = new  JaxbDataFormat("com.estafet.invoicesystem.jpa.model");

        from("activemq:incomingInvoices")
                .routeId("fraud_detection_route")
                .streamCaching()
                .unmarshal(jxb)
                .log(LoggingLevel.INFO, "Received message with body: ${body}")
                .enrich("direct:call_tax", new InvoiceValidityCheckerStrategy())
                .beanRef("fraudDetectionProcessor", "updateInvoiceStatus");

        //route for retrieval taxResponse by type
        from("direct:call_tax").streamCaching().process(new TaxRequestConstructorProcessor())
                .log("Before call to Tax Request: ${body} ").marshal(jxb).to("cxf:bean:taxRequest").end();


    }
}
