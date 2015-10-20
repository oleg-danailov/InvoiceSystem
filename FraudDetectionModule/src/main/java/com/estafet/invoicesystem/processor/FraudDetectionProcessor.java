package com.estafet.invoicesystem.processor;

import com.estafet.invoicesystem.jpa.dao.api.InvoiceDAO;
import com.estafet.invoicesystem.jpa.model.Invoice;
import com.estafet.invoicesystem.jpa.model.InvoiceResponse;
import com.estafet.taxservice.api.TaxOSGIService;
import org.apache.camel.Exchange;

/**
 * Created by estafet on 09/10/15.
 */
public class FraudDetectionProcessor {

    private InvoiceDAO invoiceDAO;
    private TaxOSGIService taxOSGIService;

    public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public void setTaxOSGIService(TaxOSGIService taxOSGIService) {
        this.taxOSGIService = taxOSGIService;
    }

    public void updateInvoiceStatus(Exchange exchange){
        InvoiceResponse invoiceRequest = exchange.getIn().getBody(InvoiceResponse.class);
        invoiceDAO.updateInvoiceStatus(invoiceRequest.getInvoiceId(), invoiceRequest.getInvoiceStatus());
    }

}
