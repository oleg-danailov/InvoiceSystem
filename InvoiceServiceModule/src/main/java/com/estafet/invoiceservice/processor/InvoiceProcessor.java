package com.estafet.invoiceservice.processor;

import com.estafet.invoicesystem.jpa.dao.api.InvoiceDAO;
import com.estafet.invoicesystem.jpa.model.Invoice;
import com.estafet.invoicesystem.jpa.model.InvoiceResponse;
import com.estafet.taxservice.api.TaxOSGIService;
import org.apache.camel.Exchange;

import java.math.BigDecimal;

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

    public void applyTax(Exchange exchange) {
        Invoice invoice = exchange.getIn().getBody(Invoice.class);
        BigDecimal amount = invoice.getInvoiceAmount();
        String invoiceType = invoice.getInvoiceType();

        BigDecimal taxPercent = taxOSGIService.getTaxByType((invoiceType == null ? "VAT" : invoiceType));
        BigDecimal taxesAmount = amount.multiply((taxPercent == null ? new BigDecimal(0.18) : taxPercent));

        invoice.setTaxesAmount(taxesAmount);
        invoice.setTotalAmount(taxesAmount.add(amount));
        System.out.println(invoice.getTotalAmount());
    }

    public void transform(Exchange exchange) {
        Invoice invoice = exchange.getIn().getBody(Invoice.class);

        InvoiceResponse invoiceResponse = new InvoiceResponse();

        invoiceResponse.setInvoiceId(invoice.getInvoiceId());
        invoiceResponse.setTaxesAmount(invoice.getTaxesAmount());
        invoiceResponse.setInvoiceAmount(invoice.getInvoiceAmount());
        invoiceResponse.setInvoiceCreationDate(invoice.getInvoiceCreationDate());
        invoiceResponse.setInvoiceNumber(invoice.getInvoiceNumber());
        invoiceResponse.setInvoiceType(invoice.getInvoiceType());
        invoiceResponse.setProviderCompany(invoice.getProviderCompany());
        invoiceResponse.setReceiverCompany(invoice.getReceiverCompany());
        invoiceResponse.setTotalAmount(invoice.getTotalAmount());
        invoiceResponse.setInvoiceStatus(invoice.getInvoiceStatus());

        exchange.getOut().setBody(invoiceResponse);
    }

    public void persistInvoice(Exchange exchange){
        Invoice invoice = exchange.getIn().getBody(Invoice.class);
        invoiceDao.saveInvoice(invoice);
    }
}
