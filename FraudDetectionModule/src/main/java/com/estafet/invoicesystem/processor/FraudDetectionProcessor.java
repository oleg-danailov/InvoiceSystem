package com.estafet.invoicesystem.processor;

import com.estafet.invoicesystem.jpa.dao.api.InvoiceDAO;
import com.estafet.invoicesystem.jpa.model.Invoice;
import org.apache.camel.Exchange;

/**
 * Created by estafet on 09/10/15.
 */
public class FraudDetectionProcessor {

    private InvoiceDAO invoiceDAO;
    public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public void updateInvoiceStatus(Exchange exchange){
        Invoice invoiceRequest = exchange.getIn().getBody(Invoice.class);
        invoiceDAO.updateInvoiceStatus(invoiceRequest.getInvoiceId(), invoiceRequest.getInvoiceStatus());
    }

}
