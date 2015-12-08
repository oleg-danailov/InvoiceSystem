package com.estafet.invoicesystem.route;

import com.estafet.invoicesystem.jpa.model.Client;
import com.estafet.invoicesystem.model.CreateClientResponse;
import com.estafet.invoicesystem.model.GetClientRequest;
import com.estafet.invoicesystem.model.GetClientResponse;
import com.estafet.invoicesystem.processor.PersistenceBean;
import com.estafet.invoicesystem.webservice.ClientSoapServiceImpl;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.DataFormat;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.AvailablePortFinder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ClientServiceRouteTest extends CamelTestSupport {

    private PersistenceBean persistenceBean;

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new ClientServiceRoute();
    }
    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry jndiRegistry= super.createRegistry();

        persistenceBean = Mockito.mock(PersistenceBean.class);
        jndiRegistry.bind("persistenceBean", persistenceBean);

        CxfEndpoint clientEndpoint = createCxfEndpoint();
        jndiRegistry.bind("clientService", clientEndpoint);

        return jndiRegistry;
    }
    private CxfEndpoint createCxfEndpoint() {
        String availablePort = String.valueOf(AvailablePortFinder.getNextAvailable());
        CxfEndpoint endpoint = new CxfEndpoint();
        endpoint.setAddress("http://localhost:" + availablePort + "/client_service");
        endpoint.setServiceClass(ClientSoapServiceImpl.class);
        endpoint.setDataFormat(DataFormat.PAYLOAD);
        return endpoint;
    }

//    @Override
//    public boolean isUseAdviceWith() {
//        return true;
//    }

    @Test
    public void testSaveClient() throws Exception {
        MockEndpoint endMock = getMockEndpoint("mock:end");

        RouteDefinition route = context.getRouteDefinition("client-service-route");
        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:cxf");
                weaveById("marshaler").after().to("mock:end");
            }
        });
        context.start();

        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {

                Exchange exchange = (Exchange) invocationOnMock.getArguments()[0];
                int clientId = 1;
                exchange.getIn().getBody(Client.class).setClientId(clientId);

                CreateClientResponse response = new CreateClientResponse();
                response.setClientId(1);
                exchange.getOut().setBody(response);
                return null;
            }
        }).when(persistenceBean).saveClient(Mockito.any(Exchange.class));

        String body =  "<client xmlns=\"http://webservice.invoicesystem.estafet.com/\">\n" +
                "            <name>testName</name>\n" +
                "            <number>1234</number>\n" +
                "           </client>";

        Exchange input = new DefaultExchange(context);
        input.getIn().setBody(body);
        input.getIn().setHeader("SOAPAction", "createClient");
        template.send("direct:cxf", input);
        endMock.expectedMessageCount(1);
        assertMockEndpointsSatisfied();

        Exchange exchange = endMock.getExchanges().get(0);
        CreateClientResponse createClientResponse =  exchange.getIn().getBody(CreateClientResponse.class);
        assertEquals(new Integer(1), createClientResponse.getClientId());

    }
    @Test
    public void testGetClient() throws Exception {

        Exchange exchange = new DefaultExchange(context);
        GetClientRequest request = new GetClientRequest();
        request.setClientId(new Integer(1));
        exchange.getIn().setBody(request);

        RouteDefinition route = context.getRouteDefinition("client-service-route");
        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:cxf");
                weaveById("marshaler").after().to("mock:end");
            }
        });
        context.start();

        MockEndpoint endMock = getMockEndpoint("mock:end");
        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Exchange exchange = (Exchange) invocationOnMock.getArguments()[0];
                GetClientRequest request = exchange.getIn().getBody(GetClientRequest.class);
                Client client = new Client();
                client.setClientId(request.getClientId());
                GetClientResponse response = new GetClientResponse();
                response.setClient(client);
                exchange.getOut().setBody(response);
                return null;
            }
        }).when(persistenceBean).getClient(Mockito.any(Exchange.class));//Mockito.any(Exchange.class)

        String body = "<getClientRequest xmlns=\"http://webservice.invoicesystem.estafet.com/\">\n" +
                "         <clientId>1</clientId>\n" +
                "      </getClientRequest>";

        Exchange input = new DefaultExchange(context);
        input.getIn().setBody(body);
        input.getIn().setHeader("SOAPAction","getClient");
        template.send("direct:cxf", input);

        endMock.expectedMessageCount(1);

        assertMockEndpointsSatisfied();
        Exchange result = endMock.getExchanges().get(0);
        GetClientResponse response = result.getIn().getBody(GetClientResponse.class);
        assertEquals(new Integer(1), response.getClient().getClientId());
    }

}