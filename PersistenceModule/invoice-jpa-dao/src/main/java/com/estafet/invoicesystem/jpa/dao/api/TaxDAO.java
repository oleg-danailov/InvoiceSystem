package com.estafet.invoicesystem.jpa.dao.api;

import com.estafet.invoicesystem.jpa.model.Tax;

import java.util.List;

/**
 *
 */
public interface TaxDAO {

    public List<Tax> getAll();

    public List<Tax> findTaxByName(String name);

    public Tax getTax(Integer id);

    public void removeTax(Integer id);

    public void saveTax(Tax tax);

    public void refreshTax(Tax tax);

}
