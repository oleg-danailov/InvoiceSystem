package com.estafet.invoicesystem.processor;

import com.estafet.invoicesystem.jpa.dao.api.ClientDAO;
import com.estafet.invoicesystem.jpa.model.Client;
import com.estafet.invoicesystem.model.CreateClientRequest;
import com.estafet.invoicesystem.model.CreateClientResponse;
import com.estafet.invoicesystem.model.GetClientRequest;
import com.estafet.invoicesystem.model.GetClientResponse;
import org.apache.camel.Exchange;

import java.util.List;

/**
 * Created by estafet on 01/12/15.
 */
public class PersistenceBean {
    private ClientDAO clientDao;

    public void setClientDao(ClientDAO clientDao) {
        this.clientDao = clientDao;
    }

    public void getClient(Exchange exchange) {

        GetClientRequest request = exchange.getIn().getBody(GetClientRequest.class);
        Client client = clientDao.getClient(request.getClientId());
        GetClientResponse response = new GetClientResponse();
        response.setClient(client);
        exchange.getOut().setBody(response);
    }


    public void saveClient(Exchange exchange) {
        Client client = exchange.getIn().getBody(CreateClientRequest.class).getClient();

        //Client client = exchange.getIn().getBody(Client.class);
        clientDao.saveClient(client);
        System.out.println();
        CreateClientResponse response = new CreateClientResponse();
        response.setClientId(client.getClientId());
        exchange.getOut().setBody(response);


    }
    public void saveClients(List<Client> clients) {
        clientDao.saveAll(clients);
    }
}
