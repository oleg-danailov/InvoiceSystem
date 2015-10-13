package com.estafet.invoicesystem.jpa.dao.impl;

import com.estafet.invoicesystem.jpa.dao.api.TaxDAO;
import com.estafet.invoicesystem.jpa.model.Tax;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

//import org.apache.commons.logging.LogFactory;

/**
 * Created by Yordan Stankov on 05/10/15.
 */
public class TaxDAOImpl implements TaxDAO {

    //private static final transient Log LOG = LogFactory.getLog(InvoiceDAOImpl.class);

    private EntityManager entityManager;

    private static final String findTaxByName = "select tax from Tax as tax where tax.taName = :tax_name";
    private static final String findTaxByReference = "select tax from Tax as tax where tax.taxId = :tax_id";
    private static final String findTax = "select tax from Tax as tax";


    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public List<Tax> findTax()
    {
        Query q = this.entityManager.createQuery(findTax);

        List list = q.getResultList();

        return list;
    }

    public List<Tax> findTaxByName(String name)
    {
        Query q = this.entityManager.createQuery(findTaxByName);
        q.setParameter("tax_name", name);
        List list = q.getResultList();

        return list;
    }

    public List<Tax> findTaxByReference(String id)
    {
        Query q = this.entityManager.createQuery(findTaxByReference);
        q.setParameter("tax_id", id);
        List list = q.getResultList();

        return list;
    }

    public Tax getTax(long id)
    {
        return (Tax)this.entityManager.find(Tax.class, Long.valueOf(id));
    }

    public void removeTax(long id)
    {
        Object record = entityManager.find(Tax.class, Long.valueOf(id));
        entityManager.remove(record);
    }

    @Override
    public void saveTax(Tax tax) {
        System.out.println("Invoice  with number :" + tax.getTaxName());
        entityManager.persist(tax);
    }

    @Override
    public void refreshTax(Tax tax) {
        entityManager.refresh(tax);
    }

}