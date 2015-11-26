package com.estafet.invoicesystem.company.processor;


import com.estafet.invoicesystem.jpa.dao.api.CompanyDAO;
import com.estafet.invoicesystem.jpa.model.Company;
import com.estafet.invoicesystem.jpa.model.DeleteCompanyResponse;
import org.apache.camel.Exchange;


/**
 * Created by Miroslava Stancheva on 23/11/15.
 */
public class CompanyProcessor {
    private CompanyDAO companyDao;

    public void setCompanyDao(CompanyDAO companyDao) {
        this.companyDao = companyDao;
    }

    public void persistCompany(Exchange exchange) {
        Company company = exchange.getIn().getBody(Company.class);
        companyDao.saveCompany(company);

        exchange.getOut().setBody(company);
    }

    public void removeCompany(Exchange exchange) {
        Company company = exchange.getIn().getBody(Company.class);
        company = companyDao.getCompanyByName(company.getCompanyName());
        DeleteCompanyResponse response = new DeleteCompanyResponse();

        try {
            companyDao.removeCompany(company.getCompanyId());
            response.setResult("success");
        } catch (Exception e) {
            response.setResult("error");
        }

        exchange.getOut().setBody(response);
    }
}
