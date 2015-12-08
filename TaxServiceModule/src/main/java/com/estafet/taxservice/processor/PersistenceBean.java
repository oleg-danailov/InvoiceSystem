package com.estafet.taxservice.processor;

import com.estafet.invoicesystem.jpa.dao.api.TaxDAO;
import com.estafet.invoicesystem.jpa.model.AdditionalTaxResponse;
import com.estafet.invoicesystem.jpa.model.Tax;
import com.estafet.invoicesystem.jpa.model.TaxResponse;
import org.apache.camel.Exchange;

/**
 * Created by estafet on 26/11/15.
 */
public class PersistenceBean {

    private TaxDAO taxDao;

    public void setTaxDao(TaxDAO taxDao) {
        this.taxDao = taxDao;
    }

    public void deleteTax(Exchange exchange){
        Integer id = exchange.getIn().getHeader("taxId", Integer.class);
        taxDao.removeTax(id);

        AdditionalTaxResponse response = new AdditionalTaxResponse();
        response.setCode("OK");

        exchange.getOut().setBody(response);
    }


}
