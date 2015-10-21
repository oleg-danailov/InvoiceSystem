package com.estafet.invoicesystem.jpa.dao.impl;

import com.estafet.invoicesystem.jpa.model.Tax;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

import static com.estafet.invoicesystem.test.JPAUnitTestingUtils.loadBaseDataSet;
import static com.estafet.invoicesystem.test.JPAUnitTestingUtils.wrapDatabaseConnection;
import static org.junit.Assert.*;

public class TaxDAOImplTest {


    protected static EntityManagerFactory entityManagerFactory;
    protected static EntityManager entityManager;
    protected static IDatabaseConnection connection;
    protected static IDataSet dataSet;
    private static TaxDAOImpl taxDAO;

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
        dataSet = loadBaseDataSet("tax_dao/base-dataset.xml");
        taxDAO = new TaxDAOImpl();
        taxDAO.setEntityManager(entityManager);
    }

    @AfterClass
    public static void closeEntityManager() throws DatabaseUnitException, SQLException {
        DatabaseOperation.DELETE_ALL.execute(connection, dataSet);

        entityManager.close();
        entityManagerFactory.close();

        taxDAO = null;
    }


    @Test
    public void testGetAll() throws Exception {
        List<Tax> list = taxDAO.getAll();
        assertTrue("Size of all entities i different then expected", list.size() == 2);
    }

    @Test
    public void testFindTaxByName(){
        Tax result = taxDAO.findTaxByName("ProfitTax");

        assertTrue("Wrong number of returned entries", result != null);
        assertEquals("", "ProfitTax", result.getTaxName());
    }

    @Test
    public void testGetTax(){

        Tax tax = taxDAO.findTaxByName("ProfitTax");
        Integer id = tax.getTaxId();
        tax = taxDAO.getTax(id);

        assertNotNull("Tax object with id = "+ id +" was not retrieved from DB", tax);

    }

    @Test
    public void testRemoveTax(){

        Tax tax = taxDAO.findTaxByName("ProfitTax");
        Integer id = tax.getTaxId();
        taxDAO.removeTax(id);

        tax = taxDAO.getTax(id);
        assertNull("Tax object with id = " + id  +" was not removed from DB", tax);
    }


    @Test
    public void testSaveTax(){
        Tax tax = new Tax();
        tax.setTaxName("TestTax");

        assertNull("taxId should be null, not inserted yet in db", tax.getTaxId());

        EntityTransaction trx = entityManager.getTransaction();
        // New transaction
        try {
            trx.begin();

            taxDAO.saveTax(tax);
            entityManager.flush();

            // Committing
            trx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            trx.rollback();
        }

        entityManager.clear();

        assertNotNull("taxId is null, object was not inserted in db", tax.getTaxId());
    }


}