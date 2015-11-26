package com.estafet.invoicesystem.company.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;


/**
 * Created by Miroslava Stancheva on 23/11/15.
 */
public class ManagementRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        JaxbDataFormat jxb = new JaxbDataFormat("com.estafet.invoicesystem.jpa.model");

        from("cxf:bean:companyRequest")
                .id("company_management_route")
                .log("inside company route  Received: ${header.SOAPAction}")
                        .choice()
                .when(header("SOAPAction").isEqualTo("http://companyservice.estafet.com/addCompanyRequest"))
               .to("direct:add_company")
               .when(header("SOAPAction").isEqualTo("http://companyservice.estafet.com/removeCompanyRequest"))
               .to("direct:remove_company");


        from("direct:add_company")
                .log("before persist")
                .streamCaching()
                .beanRef("companyProcessor", "persistCompany").id("perssistor")
                .marshal(jxb)
        .to("mock:result_add");

        from("direct:remove_company")
                .log("In Remove Company")
                .streamCaching()
                .beanRef("companyProcessor","removeCompany").id("remover")
                .marshal(jxb)
        .to("mock:result_remove");
    }
}
