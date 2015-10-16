package com.estafet.invoicesystem.jpa.dao.impl;

import com.estafet.invoicesystem.jpa.model.Invoice;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.estafet.invoicesystem.test.JPAUnitTestingUtils.*;

public class InvoiceDAOImplTest {

    protected static EntityManagerFactory entityManagerFactory;
    protected static EntityManager entityManager;
    protected static IDatabaseConnection connection;
    protected static IDataSet dataSet;
    private static InvoiceDAOImpl invoiceDAO;

    @Before
    public void cleanDB() throws Exception {
        entityManager.clear();
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
    }


    @BeforeClass
    public static void initEntityManager() {
        entityManagerFactory = Persistence.createEntityManagerFactory(
                "invoice-unit-test");
        entityManager = entityManagerFactory.createEntityManager();

        connection = wrapDatabaseConnection(entityManager);
        dataSet = loadBaseDataSet("invoice_dao/base-dataset.xml");
        invoiceDAO = new InvoiceDAOImpl();
        invoiceDAO.setEntityManager(entityManager);
    }

    @AfterClass
    public static void closeEntityManager() throws DatabaseUnitException, SQLException {
        DatabaseOperation.DELETE_ALL.execute(connection, dataSet);

        entityManager.close();
        entityManagerFactory.close();

        invoiceDAO = null;
    }

    @Test
    public void testSaveInvoice() {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber("12345678");

        assertNull("invoiceId should be null, not inserted yet in db", invoice.getInvoiceId());

        EntityTransaction trx = entityManager.getTransaction();
        // New transaction
        try {
            trx.begin();

            invoiceDAO.saveInvoice(invoice);
            entityManager.flush();

            // Committing
            trx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            trx.rollback();
        }

        entityManager.clear();

        assertNotNull("invoiceId is null, object was not inserted in db", invoice.getInvoiceId());
        //TODO add another assert
    }

    @Test
    public void testFindByNumberAndProvider() {
        Invoice invoice = invoiceDAO.findByNumberAndProvider("1234", "Estafet");

        assertEquals("Invoice type don't match VAT","VAT", invoice.getInvoiceType());
        assertEquals("Invoice number don't match 1234","1234", invoice.getInvoiceNumber());
        assertEquals("Invoice provider company don't match Estafet", "Estafet", invoice.getProviderCompany());

    }
    @Test
    public void testUpdateInvoiceStatus(){
        Invoice invoice = new Invoice();
        invoice.setInvoiceStatus("error");

        EntityTransaction trx = entityManager.getTransaction();
        // New transaction
        try {
            trx.begin();

            invoiceDAO.saveInvoice(invoice);
            entityManager.flush();
            Integer id = invoice.getInvoiceId();

            invoiceDAO.updateInvoiceStatus(id, "checked");
            entityManager.flush();

            //get from DB for checking of real status
            invoice = invoiceDAO.getInvoice(id);

            // Committing
            trx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            trx.rollback();
        }

        entityManager.clear();

        assertEquals("Status is not the same", "checked", invoice.getInvoiceStatus());

    }
    @Test
    public void testRemoveInvoice(){
        Invoice invoice = new Invoice();

        EntityTransaction trx = entityManager.getTransaction();

        Integer id = null;
        // New transaction
        try {
            trx.begin();

            invoiceDAO.saveInvoice(invoice);
            entityManager.flush();

            id = invoice.getInvoiceId();

            invoiceDAO.removeInvoice(id);

            // Committing
            trx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            trx.rollback();
        }



        entityManager.clear();

        invoice = invoiceDAO.getInvoice(id);

        assertTrue("Invoice is not removed", invoice == null);
    }

    @Test
    public void testGetAll(){
        List<Invoice> list = invoiceDAO.getAll();
        assertTrue("Size of all entities i different then expected", list.size() == 2);
    }

}