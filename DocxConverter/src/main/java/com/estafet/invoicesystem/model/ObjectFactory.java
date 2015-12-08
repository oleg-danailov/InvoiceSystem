package com.estafet.invoicesystem.model;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * Created by estafet on 01/12/15.
 */
@XmlRegistry
public class ObjectFactory {

    public UploadFileRequest createUploadFileRequest() {
        return new UploadFileRequest();
    }
    public GetClientRequest createGetClientRequest(){
        return new GetClientRequest();
    }
    public GetClientResponse createGetClientResponse(){
        return new GetClientResponse();
    }
    public CreateClientRequest createCreateClientRequest(){
        return new CreateClientRequest();
    }
    public CreateClientResponse createCreateClientResponse(){
        return new CreateClientResponse();
    }



}
