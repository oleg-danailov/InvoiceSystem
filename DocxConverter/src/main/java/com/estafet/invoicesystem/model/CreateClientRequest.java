package com.estafet.invoicesystem.model;

import com.estafet.invoicesystem.jpa.model.Client;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by estafet on 07/12/15.
 */
@XmlRootElement(name = "createClientRequest", namespace = "http://webservice.invoicesystem.estafet.com/")
public class CreateClientRequest {
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
