package com.estafet.invoicesystem.company.test;

import org.apache.openjpa.persistence.OpenJPAEntityManager;
import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Miroslava Stancheva on 25/11/15.
 */
public class JPAUnitTestingUtils {
    public static IDataSet loadBaseDataSet(String baseDataSet) {
        FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
        flatXmlDataSetBuilder.setColumnSensing(true);
        try {
            IDataSet dataset = flatXmlDataSetBuilder.build(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(baseDataSet));

            return dataset;
        } catch (DataSetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static IDatabaseConnection wrapDatabaseConnection(EntityManager entityManager) {
        OpenJPAEntityManager kem = OpenJPAPersistence.cast(entityManager);
        IDatabaseConnection connection = null;
        try {
            connection = new DatabaseConnection((Connection)kem.getConnection());
            connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());

        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        }

        return connection;

    }

    public static void validateTableWithDataSet(IDatabaseConnection connection, String tableName, String expectedDataSet) throws DatabaseUnitException, SQLException {
        FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
        flatXmlDataSetBuilder.setColumnSensing(true);
        IDataSet expectedDataset = null;
        ITable expectedTable = null;

        expectedDataset = flatXmlDataSetBuilder.build(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(tableName + "/" + expectedDataSet));
        expectedTable = expectedDataset.getTable(tableName);


        IDataSet databaseDataSet = null;
        ITable filteredTable = null;

        databaseDataSet = connection.createDataSet();

        ITable actualTable = databaseDataSet.getTable(tableName);
        filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredTable);
    }
}
