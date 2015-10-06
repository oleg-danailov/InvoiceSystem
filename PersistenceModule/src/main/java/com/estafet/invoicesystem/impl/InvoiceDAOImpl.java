package com.estafet.invoicesystem.impl;

import com.estafet.invoicesystem.api.InvoiceDAO;
import com.estafet.invoicesystem.model.Invoice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Yordan Stankov on 05/10/15.
 */
public class InvoiceDAOImpl implements InvoiceDAO {

        private static final transient Log LOG = LogFactory.getLog(InvoiceDAOImpl.class);

        @PersistenceContext
        private EntityManager entityManager;
        private static final String findInvoiceByReference = "select inv from Invoice as inv where inv.invoice_id = :inv_id";
        private static final String findInvoice = "select inv from Invoice as inv";

        public List<Invoice> findInvoice()
        {
            Query q = this.entityManager.createQuery(findInvoice);

            List list = q.getResultList();

            return list;
        }

        public List<Invoice> findInvoice(String key)
        {
            Query q = this.entityManager.createQuery(findInvoiceByReference);
            q.setParameter("inv_id", key);
            List list = q.getResultList();

            return list;
        }

        public Invoice getInvoice(long id)
        {
            return (Invoice)this.entityManager.find(Invoice.class, Long.valueOf(id));
        }

        public void removeInvoice(long id)
        {
            Object record = this.entityManager.find(Invoice.class, Long.valueOf(id));
            this.entityManager.remove(record);
            this.entityManager.flush();
        }

        public void saveInvoice(Invoice invoice) {
            this.entityManager.persist(invoice);
            this.entityManager.flush();
        }

}
