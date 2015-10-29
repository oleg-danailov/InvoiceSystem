package com.estafet.taxservice.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

/**
 * Created by Miroslava Stancheva on 27/10/15.
 */
public class AdditionalTaxServiceRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        from("cxf:bean:createTax")
                .id("additional_tax_service_route")
                .streamCaching()
                .processRef("additionalTaxProcessor")
                .marshal(new JaxbDataFormat("com.estafet.invoicesystem.jpa.model"))
                .to("mock:result");
    }
}
