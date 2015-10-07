package com.estafet.taxservice.api;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by Angelo Atanasov on 06/10/15.
 */
@WebService(targetNamespace = "http://taxservice.estafet.com/",
        portName = "TaxServiceEndpoint",
        serviceName = "TaxServiceEndpointService",
        wsdlLocation = "META-INF/wsdl/tax_service.wsdl"
 )
public interface TaxService {
    @WebMethod(action = "http://taxservice.estafet.com/taxRequest")
    public String taxRequest(@WebParam(name = "invoiceType" )String text);
}
