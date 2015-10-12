package com.estafet.invoiceservice.processor;

import com.estafet.invoicesystem.jpa.dao.api.InvoiceDAO;
import com.estafet.invoicesystem.jpa.model.Invoice;
import com.estafet.taxservice.api.TaxOSGIService;
import org.apache.camel.Exchange;

/**
 * Created by estafet on 08/10/15.
 */
public class InvoiceProcessor {

    private InvoiceDAO invoiceDao;
    private TaxOSGIService taxOSGIService;

    public void setInvoiceDao(InvoiceDAO invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    public void setTaxOSGIService(TaxOSGIService taxOSGIService) {
        this.taxOSGIService = taxOSGIService;
    }

    public void persistInvoice(Exchange exchange){
        Invoice invoice = exchange.getIn().getBody(Invoice.class);
        invoiceDao.saveInvoice(invoice);
    }
}
