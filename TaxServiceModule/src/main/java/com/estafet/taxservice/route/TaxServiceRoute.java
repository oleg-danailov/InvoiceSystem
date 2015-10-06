package com.estafet.taxservice.route;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Angelo Atanasov on 06/10/15.
 */
public class TaxServiceRoute  extends RouteBuilder {

    public void configure() throws Exception {
        // webservice responses
        from("cxf:bean:taxService")
                .id("tax_service_route")
                .log("${body}")
                .to("mock:result");
                /*.convertBodyTo(InputReportIncident.class)
                .wireTap("file://target/inbox/?fileName=request-${date:now:yyyy-MM-dd-HHmmssSSS}")
                .choice().when(simple("${body.givenName} == 'Claus'"))
                .transform(constant(ok))
                .otherwise()
                .transform(constant(accepted))*/;
    }
}
