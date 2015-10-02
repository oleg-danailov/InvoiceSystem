package com.estafet.invoicesystem.frauddetection;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.Map;

/**
 * Created by Yordan Stankov on 02/10/15.
 */
public class FraudDetectionRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("activemq:review_queue")
                .routeId("fraud_detection_route")
                .streamCaching()
                .log(LoggingLevel.INFO, "Received message with body: ${body}")
                .unmarshal().json(JsonLibrary.Jackson, Map.class)
                .end()
                .to("mock:result");
    }
}
