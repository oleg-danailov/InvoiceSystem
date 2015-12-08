package com.estafet.invoicesystem.webservice;

import com.estafet.invoicesystem.jpa.model.Client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;

/**
 * Created by estafet on 01/12/15.
 */
@WebService()
public interface FileSoapService {
    @WebMethod(action = "uploadFile")
    @RequestWrapper(localName = "uploadFileRequest")
    public String uploadFile(@WebParam(name="fileName") String fileName);
}
