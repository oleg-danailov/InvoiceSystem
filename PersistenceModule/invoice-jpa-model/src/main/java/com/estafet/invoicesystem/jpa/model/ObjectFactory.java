package com.estafet.invoicesystem.jpa.model;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * Created by estafet on 05/10/15.
 */
@XmlRegistry
public class ObjectFactory {

    public Invoice createRequest() {
        return new Invoice();
    }
    public InvoiceResponse createResponse() {
        return new InvoiceResponse();
    }

    public Tax createTax() { return new Tax(); }
    public TaxResponse createTaxResponse() { return  new TaxResponse(); }

    public GetInvoiceRequest createGetInvoice() { return new GetInvoiceRequest(); }

    public GetInvoiceResponse createGetInvoiceResponse() { return new GetInvoiceResponse(); }
    public AdditionalTaxResponse createAdditionalTaxResponse(){return new AdditionalTaxResponse();}

}
