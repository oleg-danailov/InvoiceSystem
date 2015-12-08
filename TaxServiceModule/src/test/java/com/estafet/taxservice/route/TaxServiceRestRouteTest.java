package com.estafet.taxservice.route;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaxServiceRestRouteTest extends CamelTestSupport{

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new TaxServiceRestRoute();
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    //@Test
    public void testRoute() throws Exception {
        MockEndpoint endpoint = getMockEndpoint("mock:result");
        RouteDefinition route = context.getRouteDefinition("rest-tax-service");
        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveById("create-processor").after().to("mock:result");
            }
        });
        context.start();
        template.sendBodyAndHeader("jetty:http://localhost:7654/taxes","{\"taxName\":\"test\"}", "CamelHttpMethod", "POST");
        endpoint.expectedMessageCount(1);

        assertMockEndpointsSatisfied();

        String httpMethod = endpoint.getExchanges().get(0).getIn().getHeader("CamelHttpMethod", String.class);
        assertEquals("POST", httpMethod);
    }
}