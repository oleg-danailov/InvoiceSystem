package com.estafet.taxservice.processor;

import com.estafet.invoicesystem.jpa.dao.api.TaxDAO;
import com.estafet.invoicesystem.jpa.model.Tax;
import com.estafet.invoicesystem.jpa.model.TaxRequest;
import com.estafet.invoicesystem.jpa.model.TaxResponse;
import com.estafet.taxservice.exception.InvalidTaxRequestException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

/**
 * Created by estafet on 25/11/15.
 */
public class GetTaxProcessor implements Processor {

    private TaxDAO taxDao;

    public void setTaxDao(TaxDAO taxDao) {
        this.taxDao = taxDao;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        TaxRequest tax = exchange.getIn().getBody(TaxRequest.class);
        String invoiceType = exchange.getIn().getHeader("invoiceType", String.class);//Rest request
        //TODO if null ->
        if (tax == null && invoiceType == null) {
            throw new InvalidTaxRequestException("No tax found in the request.");
        } else if (invoiceType == null || invoiceType.isEmpty()){//SOAP request
            invoiceType = tax.getInvoiceType();
        }

        List<Tax> taxes = taxDao.findTaxesByInvoiceType(invoiceType);
        if (taxes == null || taxes.size() == 0) {
            throw new InvalidTaxRequestException("No tax for this type: " + invoiceType + " found in DB.");
        }
        Tax temp = taxes.get(0);

        TaxResponse taxResponse = new TaxResponse();

        taxResponse.setTaxId(temp.getTaxId());
        taxResponse.setInvoiceType(temp.getInvoiceType());
        taxResponse.setTaxName(temp.getTaxName());
        taxResponse.setTaxPercent(temp.getTaxPercent());

        exchange.getOut().setBody(taxResponse);
    }
}
