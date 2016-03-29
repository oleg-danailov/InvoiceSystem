package com.estafet.invoiceservice.route;

import com.estafet.invoiceservice.processor.GetInvoiceProcessor;
import com.estafet.invoiceservice.processor.InvoiceProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.DataFormat;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
@Ignore
public class InvoiceServiceRouteTest extends CamelTestSupport {
    InvoiceProcessor invoiceProcessor;
    GetInvoiceProcessor getInvoiceProcessor;

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new InvoiceServiceRoute();
    }

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry registry = super.createRegistry();

        invoiceProcessor = Mockito.mock(InvoiceProcessor.class);
        getInvoiceProcessor = Mockito.mock(GetInvoiceProcessor.class);

        CxfEndpoint taxEndpoint = new CxfEndpoint();
        taxEndpoint.setAddress("http://localhost:10254/tax_service");
        taxEndpoint.setWsdlURL("file:///src/test/resources/wsdl/tax_service.wsdl");
        taxEndpoint.setDataFormat(DataFormat.PAYLOAD);

        registry.bind("taxRequest", taxEndpoint);
        registry.bind("invoiceProcessor", invoiceProcessor);
        registry.bind("getInvoiceProcessor", getInvoiceProcessor);
        return registry;
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();

        // setup the properties component to use the production file
        PropertiesComponent prop = context.getComponent("properties", PropertiesComponent.class);
        prop.setLocation("classpath:etc/invoice.properties");

        return context;
    }

  /*  @Override
    protected void addServicesOnStartup(Map<String, KeyValueHolder<Object, Dictionary>> services) {
        InvoiceDAOImpl dao = Mockito.mock(InvoiceDAOImpl.class);
        CompanyDAOImpl companyDAO = Mockito.mock(CompanyDAOImpl.class);

        KeyValueHolder serviceHolder = new KeyValueHolder(dao, null);
        KeyValueHolder companyServiceHolder = new KeyValueHolder(companyDAO, null);

        services.put(InvoiceDAO.class.getName(), serviceHolder);
        services.put(CompanyDAO.class.getName(), companyServiceHolder);
    }*/

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Test
    public void testRegisterInvoiceRoute() throws Exception {
        MockEndpoint endpoint = getMockEndpoint("mock:result_invoiceRequest");

        endpoint.expectedMessageCount(1);

        RouteDefinition route = context.getRouteDefinition("invoice_soap_route");
        Assert.assertNotNull("Route is null!" , route);
        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:start");

                interceptSendToEndpoint("direct:invoiceRoute")
                        .skipSendToOriginalEndpoint()
                        .to("mock:result_invoiceRequest");
            }
        });
        context.start();

        String requestBody = "<inv:invoiceRequest>\n" +
                "         <invoiceNumber>787899</invoiceNumber>\n" +
                "         <invoiceType>VAT</invoiceType>\n" +
                "         <currency>GBP</currency>\n" +
                "         <invoiceAmount>100</invoiceAmount>\n" +
                "         <providerCompany>Test</providerCompany>\n" +
                "         <receiverCompany>TestRes</receiverCompany>\n" +
                "      </inv:invoiceRequest>";
        Map<String, String> mapHeaders = new HashMap();
        mapHeaders.put("SOAPAction","http://invoiceservice.estafet.com/invoiceRequest");

        template.setDefaultEndpointUri("mock:result_invoiceRequest");
        template.sendBodyAndHeader("direct:start", requestBody, mapHeaders);

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testGetInvoiceRoute() throws Exception {
        MockEndpoint endpoint = getMockEndpoint("mock:result_invoiceRequest");

        endpoint.expectedMessageCount(1);

        RouteDefinition route = context.getRouteDefinition("invoice_soap_route");
        Assert.assertNotNull("Route is null!" , route);
        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:start");

                interceptSendToEndpoint("direct:invoiceRoute")
                        .skipSendToOriginalEndpoint()
                        .to("mock:result_invoiceRequest");

            }
        });
        context.start();

        String requestBody = "<inv:getInvoiceRequest>\n" +
                "    <invoiceId>18</invoiceId>\n" +
                "</inv:getInvoiceRequest>";

        Map<String, String> mapHeaders = new HashMap();
        mapHeaders.put("SOAPAction","http://invoiceservice.estafet.com/getInvoiceRequest");

        template.setDefaultEndpointUri("mock:result_invoiceRequest");
        template.sendBodyAndHeader("direct:start", requestBody, mapHeaders);

        assertMockEndpointsSatisfied();
    }
}