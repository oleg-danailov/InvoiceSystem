package com.estafet.taxservice.processor;

import com.estafet.invoicesystem.jpa.dao.api.TaxDAO;
import com.estafet.invoicesystem.jpa.model.AdditionalTaxResponse;
import com.estafet.invoicesystem.jpa.model.Tax;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by Miroslava Stancheva on 27/10/15.
 */
public class CreateTaxProcessor implements Processor {
    private TaxDAO taxDao;

    public void setTaxDao(TaxDAO taxDao) {
        this.taxDao = taxDao;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Tax tax  = exchange.getIn().getBody(Tax.class);
        System.out.println("Tax Invoice type:" + tax.getInvoiceType());

        taxDao.saveTax(tax);

        AdditionalTaxResponse response = new AdditionalTaxResponse();
        response.setCode("OK");

        exchange.getOut().setBody(response);
    }
}
