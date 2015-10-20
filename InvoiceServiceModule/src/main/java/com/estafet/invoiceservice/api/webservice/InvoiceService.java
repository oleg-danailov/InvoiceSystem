package com.estafet.invoiceservice.api.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by Angelo Atanasov on 07/10/15.
 */
@WebService(targetNamespace = "http://invoiceservice.estafet.com/",
        wsdlLocation = "META-INF/wsdl/invoice_service.wsdl"
)
public interface InvoiceService {

    @WebMethod(action = "http://invoiceservice.estafet.com/invoiceRequest")
    public String invoiceRequest(@WebParam(name = "invoiceType" ) String invoiceType,
                             @WebParam(name = "invoiceAmount") String invoiceAmount,
                             @WebParam(name = "providerCompany") String providerCompany,
                             @WebParam(name = "receiverCompany") String receiverCompany);

}
