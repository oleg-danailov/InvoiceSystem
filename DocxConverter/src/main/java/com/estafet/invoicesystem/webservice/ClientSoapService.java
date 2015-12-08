package com.estafet.invoicesystem.webservice;

import com.estafet.invoicesystem.jpa.model.Client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * Created by estafet on 30/11/15.
 */
@WebService()
public interface ClientSoapService {
    @WebMethod(action = "getClient")
    @RequestWrapper(localName = "getClientRequest")
    public Client getClient(@WebParam(name="clientId") String clientId);

    @WebMethod(action = "createClient")
    @RequestWrapper(localName = "createClientRequest")
    public Integer createClient(@WebParam(name="client") Client client);
}
