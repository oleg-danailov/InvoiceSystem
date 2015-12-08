package com.estafet.invoicesystem.model;

//import com.estafet.invoicesystem.jpa.model.Client;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by estafet on 07/12/15.
 */
@XmlRootElement(name = "createClientResponse", namespace = "http://webservice.invoicesystem.estafet.com/")
public class CreateClientResponse {

    private Integer clientId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
