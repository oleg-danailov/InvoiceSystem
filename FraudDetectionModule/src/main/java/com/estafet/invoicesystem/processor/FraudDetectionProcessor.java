package com.estafet.invoicesystem.processor;

import com.estafet.invoicesystem.jpa.dao.api.InvoiceDAO;
import com.estafet.invoicesystem.jpa.model.Invoice;
import com.estafet.taxservice.api.TaxOSGIService;
import org.apache.camel.Exchange;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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

    public void checkInvoice(Exchange exchange){
        Invoice invoice = exchange.getIn().getBody(Invoice.class);

        invoice = getInvoiceFromDB(invoice);
        boolean invoiceIsOk = checkInvoice(invoice);
        if(invoiceIsOk){
            invoice.setInvoiceStatus("checked");
        } else {
            invoice.setInvoiceStatus("error");
        }

        invoiceDAO.updateInvoiceStatus(invoice.getInvoiceId(), invoice.getInvoiceStatus());
    }

    private boolean checkInvoice(Invoice invoice) {

        BigDecimal amount = invoice.getInvoiceAmount();
        BigDecimal taxPercent = taxOSGIService.getTaxByType("VAT"); //TODO extract from invoice
        if(taxPercent == null){
            taxPercent = new BigDecimal(0.18);
        }
        BigDecimal taxesAmount = amount.multiply(taxPercent);

        MathContext mc = new MathContext(2, RoundingMode.HALF_EVEN);
        if(taxesAmount.round(mc).equals(invoice.getTaxesAmount().round(mc))){
            return true;
        }
        return false;
    }

    private Invoice getInvoiceFromDB(Invoice invoice) {
        Integer id = invoice.getInvoiceId();
        if(id != null && id > 0){
            invoice = invoiceDAO.getInvoice(id);
        } else {
            invoice = invoiceDAO.findByNumberAndProvider(invoice.getInvoiceNumber(), invoice.getProviderCompany());
        }
        return  invoice;
    }
}
