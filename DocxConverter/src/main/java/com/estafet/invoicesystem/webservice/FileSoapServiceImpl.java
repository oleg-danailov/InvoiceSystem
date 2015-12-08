package com.estafet.invoicesystem.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.soap.MTOM;

/**
 * Created by estafet on 01/12/15.
 */
@MTOM
@WebService(endpointInterface = "com.estafet.invoicesystem.webservice.FileSoapService", serviceName = "fileService")
public class FileSoapServiceImpl implements FileSoapService {
    @Override
    @WebMethod()
    @WebResult(name = "uploadFileResponse")
    @ResponseWrapper(className = "java.lang.String")
    public String uploadFile(@WebParam(name = "fileName") String fileName) {
        return null;
    }
}
