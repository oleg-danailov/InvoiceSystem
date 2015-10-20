package com.estafet.invoiceservice.api.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by Angelo Atanasov on 12/10/15.
 */
@WebService(targetNamespace = "http://invoiceservice.estafet.com/",
        wsdlLocation = "META-INF/wsdl/invoice_service.wsdl"
)
public interface GetInvoiceService {

    @WebMethod(operationName = "getInvoiceRequest", action = "http://getinvoiceservice.estafet.com/getInvoiceRequest")
    public String getInvoiceRequest(@WebParam(name = "invoiceId" ) String invoiceType);
}
