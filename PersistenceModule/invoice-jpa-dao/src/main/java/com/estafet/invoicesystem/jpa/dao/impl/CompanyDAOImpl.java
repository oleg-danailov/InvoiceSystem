package com.estafet.invoicesystem.jpa.dao.impl;

import com.estafet.invoicesystem.jpa.dao.api.CompanyDAO;
import com.estafet.invoicesystem.jpa.model.Company;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Created by Miroslava Stancheva on 18/11/15.
 */
public class CompanyDAOImpl implements CompanyDAO {
    private static final String findCompanyByName = "SELECT company FROM Company AS company WHERE company.companyName = :companyName";

    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void saveCompany(Company company) {
        entityManager.persist(company);
    }

    @Override
    public Company getCompanyByName(String companyName) {
        Query q = this.entityManager.createQuery(findCompanyByName);
        q.setParameter("companyName", companyName);
        Object company;
        try {
            company = q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        return (Company) company;

    }

    @Override
    public void removeCompany(Integer id){
        Object record = entityManager.find(Company.class, id);
        entityManager.remove(record);
    }
}
