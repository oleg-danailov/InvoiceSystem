package com.estafet.invoiceservice.processor;

import com.estafet.invoicesystem.jpa.dao.api.CompanyDAO;
import com.estafet.invoicesystem.jpa.dao.api.InvoiceDAO;
import com.estafet.invoicesystem.jpa.model.Invoice;
import org.apache.camel.Exchange;

/**
 * Created by estafet on 08/10/15.
 */
public class InvoiceProcessor {

    private InvoiceDAO invoiceDao;
    private CompanyDAO companyDao;

    public void setInvoiceDao(InvoiceDAO invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    public void setCompanyDao(CompanyDAO companyDao) {
        this.companyDao = companyDao;
    }

    public void persistInvoice(Exchange exchange){
        Invoice invoice = exchange.getIn().getBody(Invoice.class);

        invoice.setReceiverCompany(companyDao.getCompanyByName(invoice.getReceiverCompany().getCompanyName()));
        invoice.setProviderCompany(companyDao.getCompanyByName(invoice.getProviderCompany().getCompanyName()));

        invoiceDao.saveInvoice(invoice);
    }
}
