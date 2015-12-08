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

@WebService(endpointInterface = "com.estafet.invoicesystem.webservice.ClientSoapService", serviceName = "clientService", targetNamespace = "http://webservice.invoicesystem.estafet.com/")
public class ClientSoapServiceImpl implements ClientSoapService{

    @Override
    @WebMethod()
    @WebResult(name = "getClientResponse", partName = "client")
    @ResponseWrapper(className = "com.estafet.invoicesystem.jpa.model.Client")
    public Client getClient(@WebParam(name = "clientId") String clientId) {
        return null;
    }

    @Override
    @WebMethod()
    @WebResult(name = "createClientResponse", partName = "client")
    @ResponseWrapper(className = "java.lang.Integer")
    public Integer createClient(@WebParam(name = "client") Client client) {
        return null;
    }


}
