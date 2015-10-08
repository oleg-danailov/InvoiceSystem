package com.estafet.invoicesystem.jpa.dao.api;

import com.estafet.invoicesystem.jpa.model.Invoice;
import com.estafet.invoicesystem.jpa.model.Tax;

import java.util.List;

/**
 * Created by Angelo Atanasov on 08/10/15.
 */
public interface TaxDAO {

    public List<Invoice> findTax();

    public List<Invoice> findTaxByName(String name);

    public List<Invoice> findTaxByReference(String id);

    public Tax getTax(long id);

    public void removeTax(long id);

    public void saveTax(Tax tax);

}
