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

    public void checkInvoice(Exchange exchange){
        Invoice invoice = exchange.getIn().getBody(Invoice.class);
        Integer id = invoice.getInvoiceId();
        if(id != null && id > 0){
            invoice = invoiceDAO.getInvoice(id);
            System.out.println("Invoice got from db: " +invoice.getInvoiceId());
        } else {
            invoice = invoiceDAO.findByNumberAndProvider(invoice.getInvoiceNumber(), invoice.getProviderCompany());
            System.out.println("Invoice got from db by number and provider: " +invoice.getInvoiceId());
        }
    }
}
