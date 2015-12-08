package com.estafet.invoicesystem.processor;

import com.estafet.invoicesystem.jpa.dao.api.ClientDAO;
import  com.estafet.invoicesystem.jpa.model.Client;
import com.estafet.invoicesystem.model.CreateClientRequest;
import com.estafet.invoicesystem.model.CreateClientResponse;
import com.estafet.invoicesystem.model.GetClientRequest;
import com.estafet.invoicesystem.model.GetClientResponse;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by estafet on 01/12/15.
 */
public class PersistenceBeanTest {
    private ClientDAO dao;
    private Client client;
    private PersistenceBean bean;
    private Exchange exchange;

    @Before
    public void setUp(){
        dao = Mockito.mock(ClientDAO.class);
        client = new Client();
        client.setName("TestName");
        client.setNumber("1234");

        bean = new PersistenceBean();

        exchange = new DefaultExchange(new DefaultCamelContext());
        exchange.setPattern(ExchangePattern.InOut);

    }
    @Test
    public void testSaveClients(){

        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        clients.add(new Client());
        clients.add(new Client());
        clients.add(new Client());

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Client> clients = (List<Client>) invocationOnMock.getArguments()[0];
                int counter = 1;
                for(Client client : clients){
                    client.setClientId(counter);
                    counter ++;
                }
                return null;
            }
        }).when(dao).saveAll(clients);

        bean.setClientDao(dao);

        bean.saveClients(clients);

        Integer previousId = null;
        for(Client client : clients){
            Integer currentId = client.getClientId();
            assertNotEquals(currentId, previousId);
            previousId = currentId;
        }
    }

    @Test
    public void testGetClient(){
        client.setClientId(1);

        Mockito.when(dao.getClient(1)).thenReturn(client);

        bean.setClientDao(dao);
        GetClientRequest request = new GetClientRequest();
        request.setClientId(new Integer("1"));
        exchange.getIn().setBody(request);
        bean.getClient(exchange);
        Client result = exchange.getOut().getBody(GetClientResponse.class).getClient();

        assertEquals("TestName", result.getName());
        assertEquals("1234", result.getNumber());
        assertEquals(new Integer("1"), result.getClientId());
    }
    @Test
    public void testSaveClient(){
        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Client arg = (Client) invocationOnMock.getArguments()[0];

                arg.setClientId(1);

                return null;
            }
        }).when(dao).saveClient(client);
        bean.setClientDao(dao);
        CreateClientRequest request = new CreateClientRequest();
        request.setClient(client);
        exchange.getIn().setBody(request);
        bean.saveClient(exchange);

        assertNotNull("clientId shouldn't be null", exchange.getOut().getBody(CreateClientResponse.class));


    }



}
