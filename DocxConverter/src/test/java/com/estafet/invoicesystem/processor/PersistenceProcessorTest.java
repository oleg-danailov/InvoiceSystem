package com.estafet.invoicesystem.processor;

import com.estafet.invoicesystem.jpa.dao.api.ClientDAO;
import com.estafet.invoicesystem.jpa.model.Client;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PersistenceProcessorTest {

    @Test
    public void testSaveClients(){
        ClientDAO dao = Mockito.mock(ClientDAO.class);

        PersistenceProcessor processor = new PersistenceProcessor();
        processor.setDao(dao);
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        clients.add(new Client());
        clients.add(new Client());
        clients.add(new Client());

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Client> clients = (List<Client>) invocationOnMock.getArguments()[0];
                for(Client client : clients){
                    client.setClientId((int) (Math.random() * 100));
                }
                return null;
            }
        }).when(dao).saveAll(clients);


        processor.saveClients(clients);

        Integer previousId = null;
        for(Client client : clients){
            Integer currentId = client.getClientId();
            assertNotEquals(currentId, previousId);
            previousId = currentId;
        }
    }
}