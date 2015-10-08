package com.estafet.invoicesystem.processor;

import com.estafet.invoicesystem.jpa.dao.api.InvoiceDAO;
import com.estafet.invoicesystem.jpa.model.Invoice;
import org.apache.camel.Exchange;

/**
 * Created by estafet on 08/10/15.
 */
public class InvoiceJpaProcessor {

    private InvoiceDAO invoiceDao;

    public void setInvoiceDao(InvoiceDAO invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    public void persistInvoice(Exchange exchange){
        Invoice invoice = exchange.getIn().getBody(Invoice.class);
        invoiceDao.saveInvoice(invoice);
    }
}
