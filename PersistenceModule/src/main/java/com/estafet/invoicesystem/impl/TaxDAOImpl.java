package com.estafet.invoicesystem.impl;

import com.estafet.invoicesystem.api.TaxDAO;
import com.estafet.invoicesystem.model.Tax;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Angelo Atanasov on 05/10/15.
 */
public class TaxDAOImpl implements TaxDAO {

    private static final transient Log LOG = LogFactory.getLog(TaxDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;
    private static final String findTaxByName = "select tax from TAX as tax where tax.tax_name = :tax_name";
    private static final String findTax = "select tax from Tax as tax";

    public List<Tax> findTax()
    {
        Query q = this.entityManager.createQuery(findTax);

        List list = q.getResultList();

        return list;
    }

    public List<Tax> findTax(String key)
    {
        Query q = this.entityManager.createQuery(findTaxByName);
        q.setParameter("tax_name", key);
        List list = q.getResultList();

        return list;
    }

    public Tax getTax(long id)
    {
        return (Tax)this.entityManager.find(Tax.class, Long.valueOf(id));
    }

    public void removeTax(long id)
    {
        Object record = this.entityManager.find(Tax.class, Long.valueOf(id));
        this.entityManager.remove(record);
        this.entityManager.flush();
    }

    public void saveTax(Tax tax) {
        this.entityManager.persist(tax);
        this.entityManager.flush();
    }
}
