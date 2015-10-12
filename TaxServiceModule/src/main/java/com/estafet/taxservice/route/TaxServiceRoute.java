package com.estafet.taxservice.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Angelo Atanasov on 06/10/15.
 */
public class TaxServiceRoute  extends RouteBuilder {
    private static String ECHO_RESPONSE = "<tax:taxResponse xmlns:tax=\"http://taxservice.estafet.com/\">\n" +
            "         <percent>result</percent>\n" +
            "      </tax:taxResponse>";

    @Override
    public void configure() throws Exception {
        // webservice responses
        from("cxf:bean:taxRequest")
                .id("tax_service_route")
                .log("${body}")
                .process(new Processor() {
                             @Override
                             public void process(Exchange exchange) throws Exception {
                                 System.out.println(exchange.getIn().getBody(String.class));
                             }
                         }

                ).to("mock:result");
                }
    }
