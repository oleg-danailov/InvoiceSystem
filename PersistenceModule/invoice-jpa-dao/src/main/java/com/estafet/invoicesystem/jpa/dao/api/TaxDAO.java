package com.estafet.invoicesystem.jpa.dao.api;

import com.estafet.invoicesystem.jpa.model.Tax;

import java.util.List;

/**
 * Created by Angelo Atanasov on 08/10/15.
 */
public interface TaxDAO {

    public List<Tax> findTax();

    public List<Tax> findTaxByName(String name);

    public List<Tax> findTaxByReference(String id);

    public Tax getTax(long id);

    public void removeTax(long id);

    public void saveTax(Tax tax);

    public void refreshTax(Tax tax);

}
