package com.estafet.invoicesystem.route;

import com.estafet.invoicesystem.jpa.dao.api.InvoiceDAO;
import com.estafet.invoicesystem.jpa.dao.impl.InvoiceDAOImpl;
import com.estafet.invoicesystem.jpa.model.TaxResponse;
import com.estafet.invoicesystem.processor.FraudDetectionProcessor;
import com.estafet.invoicesystem.strategy.InvoiceValidityCheckerStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.test.AvailablePortFinder;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.KeyValueHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Dictionary;
import java.util.Map;

import static org.junit.Assert.*;

public class FraudDetectionRouteBuilderTest extends CamelBlueprintTestSupport {

    private int availablePort =0;
    private InvoiceValidityCheckerStrategy strategy;

    @Before
    public void init(){
        availablePort = AvailablePortFinder.getNextAvailable();
    }

    @Override
    protected String getBlueprintDescriptor() {
        return  "OSGI-INF/blueprint/blueprint.xml";
    }

    @Override
    protected void addServicesOnStartup(Map<String, KeyValueHolder<Object, Dictionary>> services) {
        InvoiceDAOImpl dao = Mockito.mock(InvoiceDAOImpl.class);

        KeyValueHolder serviceHolder = new KeyValueHolder(dao, null);
        services.put(InvoiceDAO.class.getName(), serviceHolder);


    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                String taxResponse = "<ns2:taxRequestResponse xmlns:ns2=\"http://taxservice.estafet.com/\" xmlns:ns3=\"http://invoiceservice.estafet.com/\">\n" +
                        "         <invoiceType>DDS</invoiceType>\n" +
                        "         <taxId>6</taxId>\n" +
                        "         <taxName>DDS</taxName>\n" +
                        "         <taxPercent>0.22</taxPercent>\n" +
                        "      </ns2:taxRequestResponse>";


                from("direct:mock_call_tax").transform().constant(taxResponse);
            }
        };
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

     @Test
    public void testCallTaxRoute() throws Exception {

        MockEndpoint endpoint = getMockEndpoint("mock:cxf_bean_taxRequest");
        RouteDefinition route = context.getRouteDefinition("call_tax_route");
        route.adviceWith(context, new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("cxf:*")
                        .skipSendToOriginalEndpoint()
                        .to("mock:cxf_bean_taxRequest");
            }
        });
        context.start();


        String requestBody = "<ns2:invoiceRequest xmlns:ns2=\"http://invoiceservice.estafet.com/\">\n" +
                "    <invoiceAmount>200</invoiceAmount>\n" +
                "    <invoiceNumber>12345</invoiceNumber>\n" +
                "    <invoiceType>DDS</invoiceType>\n" +
                "    <providerCompany>Estafet</providerCompany>\n" +
                "    <receiverCompany>Mtel</receiverCompany>\n" +
                "</ns2:invoiceRequest>";

        template.sendBody("direct:call_tax", requestBody);

        endpoint.expectedMessageCount(1);

        assertMockEndpointsSatisfied();
    }


    @Test
    public void testFraudDetectionRouteAll() throws Exception {

        RouteDefinition route = context.getRouteDefinition("fraud_detection_route");

        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:incomingInvoices");

                weaveById("enricher").replace().enrich("direct:mock_call_tax", new AggregationStrategy() {
                    @Override
                    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
                        TaxResponse taxResponse = newExchange.getIn().getBody(TaxResponse.class);
                        System.out.println("newExchange" + taxResponse.getTaxName());
                        return oldExchange;
                    }
                });

                weaveById("updater").after().to("mock:new_end");


            }
        });

        context.start();
        MockEndpoint endpoint = getMockEndpoint("mock:new_end");

        String requestBody = "<ns2:invoiceRequest xmlns:ns2=\"http://invoiceservice.estafet.com/\">\n" +
                "    <invoiceAmount>200</invoiceAmount>\n" +
                "    <invoiceNumber>12345</invoiceNumber>\n" +
                "    <invoiceType>DDS</invoiceType>\n" +
                "    <providerCompany>Estafet</providerCompany>\n" +
                "    <receiverCompany>Mtel</receiverCompany>\n" +
                "</ns2:invoiceRequest>";

        template.sendBody("direct:incomingInvoices", requestBody);

        endpoint.expectedMessageCount(1);
        assertMockEndpointsSatisfied();
    }
}