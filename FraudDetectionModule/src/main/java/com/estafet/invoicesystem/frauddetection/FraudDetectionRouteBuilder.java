package com.estafet.invoicesystem.frauddetection;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.Map;

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
                .log(LoggingLevel.INFO, "Received message with body: ${body}")
                .beanRef("fraudDetectionProcessor", "checkInvoice");
    }
}
