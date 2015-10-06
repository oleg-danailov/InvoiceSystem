package com.estafet.invoicesystem.api;

import com.estafet.invoicesystem.model.Tax;

import java.util.List;

/**
 * Created by Yordan Stankov on 05/10/15.
 */
public interface TaxDAO {

    public List<Tax> findTax();

    public List<Tax> findTax(String key);

    public Tax getTax(long id);

    public void removeTax(long id);

    public void saveTax(Tax tax);

}
