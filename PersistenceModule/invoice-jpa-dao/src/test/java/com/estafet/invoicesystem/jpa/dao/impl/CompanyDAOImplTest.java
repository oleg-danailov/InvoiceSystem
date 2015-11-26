package com.estafet.invoicesystem.jpa.dao.impl;

import com.estafet.invoicesystem.jpa.model.Company;
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

import static com.estafet.invoicesystem.test.JPAUnitTestingUtils.loadBaseDataSet;
import static com.estafet.invoicesystem.test.JPAUnitTestingUtils.wrapDatabaseConnection;
import static org.junit.Assert.*;

/**
 * Created by Miroslava Stancheva on 19/11/15.
 */
public class CompanyDAOImplTest {

    protected static EntityManagerFactory entityManagerFactory;
    protected static EntityManager entityManager;
    protected static IDatabaseConnection connection;
    protected static IDataSet dataSet;
    private static CompanyDAOImpl companyDAO;

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
        dataSet = loadBaseDataSet("company_dao/base-dataset.xml");
        companyDAO = new CompanyDAOImpl();
        companyDAO.setEntityManager(entityManager);
    }

    @AfterClass
    public static void closeEntityManager() throws DatabaseUnitException, SQLException {
        DatabaseOperation.DELETE_ALL.execute(connection, dataSet);

        entityManager.close();
        entityManagerFactory.close();

        companyDAO = null;
    }

    @Test
    public void testSaveCompany(){
        Company company = new Company();
        company.setCompanyName("TestCompany");
        company.setAddress("TestAddress3");

        assertNull("companyId should be null, not inserted yet in db ", company.getCompanyId());

        EntityTransaction trx = entityManager.getTransaction();
         // New transaction
        try {
            trx.begin();


            companyDAO.saveCompany(company);
            entityManager.flush();

            // Committing
            trx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            trx.rollback();
        }

        entityManager.clear();

        assertNotNull("companyId is null, object was not inserted in db", company.getCompanyId());
    }

    @Test
    public void testRemoveCompany(){
        Company  company = new Company();
        company.setCompanyName("Name");
        company.setAddress("Address");

        EntityTransaction trx = entityManager.getTransaction();

        try {
            trx.begin();

            companyDAO.saveCompany(company);
            entityManager.flush();

            companyDAO.removeCompany(company.getCompanyId());

            // Committing
            trx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            trx.rollback();
        }

        entityManager.clear();

        company = companyDAO.getCompanyByName("Name");

        assertTrue("Invoice is not removed", company == null);

    }

    @Test
    public void testFindCompanyByName(){
        Company company=null;
        EntityTransaction trx = entityManager.getTransaction();

        try {
            trx.begin();

           company = companyDAO.getCompanyByName("Estafet");


            // Committing
            trx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            trx.rollback();
        }

        entityManager.clear();

        assertNotNull(company);
        assertEquals(company.getCompanyName(), "Estafet");
    }
}
