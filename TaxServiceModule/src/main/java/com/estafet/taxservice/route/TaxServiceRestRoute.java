package com.estafet.taxservice.route;

import com.estafet.invoicesystem.jpa.model.AdditionalTaxResponse;
import com.estafet.invoicesystem.jpa.model.Tax;
import com.estafet.invoicesystem.jpa.model.TaxResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 * Created by estafet on 24/11/15.
 */
public class TaxServiceRestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("jetty:http://localhost:7654/taxes").routeId("rest-tax-service")
                .choice()
                .when(header("CamelHttpMethod").isEqualTo("POST")).unmarshal().json(JsonLibrary.Jackson, Tax.class)
                    .processRef("createTaxProcessor").id("create-processor")
                    .marshal().json(JsonLibrary.Jackson, AdditionalTaxResponse.class).endChoice()
                .when(header("CamelHttpMethod").isEqualTo("GET")).processRef("getTaxProcessor").id("get-processor")
                    .marshal().json(JsonLibrary.Jackson, TaxResponse.class).endChoice()
                .when(header("CamelHttpMethod").isEqualTo("DELETE")).beanRef("persistenceBean", "deleteTax")
                    .marshal().json(JsonLibrary.Jackson, AdditionalTaxResponse.class).endChoice();

    }
}
