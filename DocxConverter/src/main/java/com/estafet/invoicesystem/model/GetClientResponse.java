package com.estafet.invoicesystem.model;

import com.estafet.invoicesystem.jpa.model.Client;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by estafet on 07/12/15.
 */
@XmlRootElement(name = "getClientResponse", namespace = "http://webservice.invoicesystem.estafet.com/")
public class GetClientResponse {

    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
