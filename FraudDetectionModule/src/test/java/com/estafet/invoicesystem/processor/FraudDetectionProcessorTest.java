package com.estafet.invoicesystem.processor;

import com.estafet.invoicesystem.jpa.dao.impl.InvoiceDAOImpl;
import com.estafet.invoicesystem.jpa.model.Invoice;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

import static com.estafet.invoicesystem.test.JPAUnitTestingUtils.*;

public class FraudDetectionProcessorTest {

    protected static EntityManagerFactory entityManagerFactory;
    protected static EntityManager entityManager;
    protected static IDatabaseConnection connection;
    protected static IDataSet dataSet;
    private static InvoiceDAOImpl invoiceDAO;
    private static FraudDetectionProcessor processor;


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
        dataSet = loadBaseDataSet("fraud_detection_processor/base-dataset.xml");
        invoiceDAO = new InvoiceDAOImpl();
        invoiceDAO.setEntityManager(entityManager);
        processor = new FraudDetectionProcessor();
        processor.setInvoiceDAO(invoiceDAO);
    }

    @AfterClass
    public static void closeEntityManager() throws DatabaseUnitException, SQLException {
        DatabaseOperation.DELETE_ALL.execute(connection, dataSet);

        entityManager.close();
        entityManagerFactory.close();

        invoiceDAO = null;
        processor = null;
    }

    @Test
    public void testUpdateInvoiceStatus() throws Exception {

        Invoice invoice = invoiceDAO.findByNumberAndProvider("1234","Estafet");
        Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        exchange.getIn().setBody(invoice);
        invoice.setInvoiceStatus("checked");

        processor.updateInvoiceStatus(exchange);

        invoice = invoiceDAO.findByNumberAndProvider("1234","Estafet");

        Assert.assertEquals("checked",invoice.getInvoiceStatus());

    }
}