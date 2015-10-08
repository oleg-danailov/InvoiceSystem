package com.estafet.invoicesystem.routebuilders;


import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import  org.apache.camel.converter.jaxb.*;
/**
 * Created by estafet on 07/10/15.
 */
public class JettyRestToJPARoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {
//        from("jetty://http:0.0.0.0:8080/invoice?jettyRestToJPARoute=POST").routeId("jetty-to-jpaPost")
//                .unmarshal().json(JsonLibrary.Jackson, Invoice.class)
//                .beanRef("invoiceDAOService","saveInvoice").end();

        //from("jetty://http:?jettyRestToJPARoute=GET").routeId("jetty-to-jpaGet").end();

        JaxbDataFormat jxb = new  JaxbDataFormat("com.estafet.invoicesystem.jpa.model");

        from("file:///u01/xml/").log(LoggingLevel.INFO, "Resieved message body: ${body}")
                .unmarshal(jxb).log(LoggingLevel.INFO, "Resieved message body: ${body}")
                .beanRef("invoiceJpaProcessor", "persistInvoice");//.beanRef("invoiceDAOService", "saveInvoice");
    }
}
