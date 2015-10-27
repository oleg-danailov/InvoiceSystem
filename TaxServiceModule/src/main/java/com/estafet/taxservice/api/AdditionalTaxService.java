package com.estafet.taxservice.api;

/**
 * Created by Miroslava Stancheva on 26/10/15.
 */

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://taxservice.estafet.com/",
        portName = "TaxServiceEndpoint",
        serviceName = "TaxServiceEndpointService",
        wsdlLocation = "META-INF/wsdl/tax_service.wsdl")
public interface AdditionalTaxService {

    @WebMethod(action = "http://taxservice.estafet.com/createTax")
    public String additionalTaxRequest(@WebParam(name = "taxName" )String textTaxName
            , @WebParam(name = "invoiceType" )String textInvoiceType
            , @WebParam(name = "taxPercent" )String textTaxPercent);
}
