package com.estafet.invoicesystem.route;

import com.estafet.invoicesystem.jpa.model.Client;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

/**
 * Created by estafet on 01/12/15.
 */
public class ClientServiceRoute extends RouteBuilder{
    @Override
    public void configure() throws Exception {
        JaxbDataFormat jxb = new JaxbDataFormat("com.estafet.invoicesystem.model");
        from("cxf:bean:clientService").streamCaching().routeId("client-service-route").log(LoggingLevel.INFO, "before choice")
                .unmarshal(jxb).choice()
                .when(header("SOAPAction").isEqualTo("getClient")).log(LoggingLevel.INFO, "into getClient")
                    .beanRef("persistenceBean", "getClient").endChoice().log(LoggingLevel.INFO, "after end choice getClient body: ${body}")
                .when(header("SOAPAction").isEqualTo("createClient")).log(LoggingLevel.INFO, "into saveClient")
                    .beanRef("persistenceBean", "saveClient").endChoice().end()
        .log(LoggingLevel.INFO, "before marshal: body ${body}").marshal(jxb).id("marshaler");
    }
}
