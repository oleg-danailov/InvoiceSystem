package com.estafet.invoicesystem.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by estafet on 07/12/15.
 */
@XmlRootElement(name = "getClientRequest", namespace = "http://webservice.invoicesystem.estafet.com/")
public class GetClientRequest {

    private Integer clientId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
