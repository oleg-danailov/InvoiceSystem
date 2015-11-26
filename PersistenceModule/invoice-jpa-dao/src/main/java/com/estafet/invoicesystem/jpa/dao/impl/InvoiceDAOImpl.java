package com.estafet.invoicesystem.jpa.dao.impl;

import com.estafet.invoicesystem.jpa.dao.api.CompanyDAO;
import com.estafet.invoicesystem.jpa.dao.api.InvoiceDAO;
import com.estafet.invoicesystem.jpa.model.Company;
import com.estafet.invoicesystem.jpa.model.Invoice;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

//import org.apache.commons.logging.LogFactory;

/**
* Created by Yordan Stankov on 05/10/15.
*/
public class InvoiceDAOImpl implements InvoiceDAO {

        //private static final transient Log LOG = LogFactory.getLog(InvoiceDAOImpl.class);

        private EntityManager entityManager;
        private CompanyDAO companyDAO;

        private static final String findInvoiceByReference = "select inv from Invoice as inv where inv.invoiceId = :inv_id";
        private static final String findInvoice = "select inv from Invoice as inv";


    public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
}

    public EntityManager getEntityManager() {
    return entityManager;
}

    public CompanyDAO getCompanyDAO() {
        return companyDAO;
    }

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public List<Invoice> getAll(){
        Query q = this.entityManager.createQuery(findInvoice);

        List list = q.getResultList();

        return list;
    }


    @Override
    public Invoice findByNumberAndProvider(String number, String provider) {

        Query query = entityManager
                .createNamedQuery("invoice.findByNumberAndProvider");
        query.setParameter("invoiceNumber",number);
        query.setParameter("providerCompany", provider) ;

        return (Invoice) query.getSingleResult();
    }

    public Invoice getInvoice(Integer id){
        return entityManager.find(Invoice.class, id);
    }

    public void removeInvoice(Integer id){
        Object record = entityManager.find(Invoice.class, id);
        entityManager.remove(record);
    }

    @Override
    public void saveInvoice(Invoice invoice) {
        Company provider= companyDAO.getCompanyByName(invoice.getProviderCompany().getCompanyName());
        invoice.setProviderCompany(provider);

        Company receiver= companyDAO.getCompanyByName(invoice.getReceiverCompany().getCompanyName());
        invoice.setReceiverCompany(receiver);

        entityManager.persist(invoice);
    }

    @Override
    public void updateInvoiceStatus(Integer id, String status) {
        Invoice invoice =  entityManager.find(Invoice.class, id);
        invoice.setInvoiceStatus(status);
        saveInvoice(invoice);
    }
    @Override
    public void refreshInvoice(Invoice invoice) {
        entityManager.refresh(invoice);
    }

}
