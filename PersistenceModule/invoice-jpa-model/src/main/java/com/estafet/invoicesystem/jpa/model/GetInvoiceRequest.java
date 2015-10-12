package com.estafet.invoicesystem.jpa.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Angelo Atanasov on 12/10/15.
 */
@XmlRootElement(name = "getInvoiceRequest", namespace = "http://invoiceservice.estafet.com/")
@XmlType
public class GetInvoiceRequest {

    private String invoiceId;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
}
