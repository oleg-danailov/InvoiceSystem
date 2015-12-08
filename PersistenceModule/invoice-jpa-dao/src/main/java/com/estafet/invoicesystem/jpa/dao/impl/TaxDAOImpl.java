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

    private static final String findTaxByName = "select tax from Tax as tax where tax.taxName = :tax_name";
    private static final String findTaxesByInvoiceName = "select tax from Tax as tax where tax.invoiceType = :invoice_type";
    private static final String findTax = "select tax from Tax as tax";

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public List<Tax> getAll(){
        Query q = this.entityManager.createQuery(findTax);

        List<Tax> list = q.getResultList();

        return list;
    }

    public List<Tax> findTaxesByInvoiceType(String invoiceType) {
        Query q = this.entityManager.createQuery(findTaxesByInvoiceName);
        q.setParameter("invoice_type", invoiceType);
        List<Tax> result = q.getResultList();
        return result;
    }

    public Tax findTaxByName(String name){
        Query q = this.entityManager.createQuery(findTaxByName);
        q.setParameter("tax_name", name);

        return (Tax)q.getSingleResult();
    }

    public Tax getTax(Integer id){
        return entityManager.find(Tax.class, id);
    }

    public void removeTax(Integer id){
        Object record = entityManager.find(Tax.class, id);
        entityManager.remove(record);
    }

    @Override
    public void saveTax(Tax tax) {
        entityManager.persist(tax);
    }

    @Override
    public void refreshTax(Tax tax) {
        entityManager.refresh(tax);
    }

}