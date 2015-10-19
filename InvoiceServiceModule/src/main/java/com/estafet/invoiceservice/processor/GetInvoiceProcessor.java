package com.estafet.invoiceservice.processor;

import com.estafet.invoiceservice.exception.IncorrectRequestException;
import com.estafet.invoicesystem.jpa.dao.api.InvoiceDAO;
import com.estafet.invoicesystem.jpa.model.GetInvoiceRequest;
import com.estafet.invoicesystem.jpa.model.GetInvoiceResponse;
import com.estafet.invoicesystem.jpa.model.Invoice;
import org.apache.camel.Exchange;

import java.util.List;

/**
 * Created by Angelo Atanasov on 12/10/15.
 */
public class GetInvoiceProcessor {
    private InvoiceDAO invoiceDao;

    public void setInvoiceDao(InvoiceDAO invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    public void getInvoice(Exchange exchange) {
        GetInvoiceRequest invoiceRequest = exchange.getIn().getBody(GetInvoiceRequest.class);

        Invoice temp = invoiceDao.getInvoice(Integer.parseInt(invoiceRequest.getInvoiceId()));

        if (temp != null) {
            GetInvoiceResponse invoiceResponse = new GetInvoiceResponse();

            invoiceResponse.setInvoiceId(temp.getInvoiceId());
            invoiceResponse.setTaxesAmount(temp.getTaxesAmount());
            invoiceResponse.setInvoiceAmount(temp.getInvoiceAmount());
            invoiceResponse.setInvoiceCreationDate(temp.getInvoiceCreationDate());
            invoiceResponse.setInvoiceNumber(temp.getInvoiceNumber());
            invoiceResponse.setInvoiceType(temp.getInvoiceType());
            invoiceResponse.setProviderCompany(temp.getProviderCompany());
            invoiceResponse.setReceiverCompany(temp.getReceiverCompany());
            invoiceResponse.setTotalAmount(temp.getTotalAmount());
            invoiceResponse.setInvoiceStatus(temp.getInvoiceStatus());

            exchange.getOut().setBody(invoiceResponse);
        } else {
            throw new IncorrectRequestException("Invoice not found");
        }

    }
}
