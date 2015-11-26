package com.estafet.invoicesystem.jpa.dao.api;

import com.estafet.invoicesystem.jpa.model.Company;

/**
 * Created by Miroslava Stancheva on 18/11/15.
 */
public interface CompanyDAO {

    public void saveCompany(Company company);

    public void removeCompany(Integer id);

    public Company getCompanyByName(String companyName);
}
