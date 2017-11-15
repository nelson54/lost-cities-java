package com.github.nelson54.lostcities.test.DbExporter;

    import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DbUnitExporter {
    private static String testDir        = "testData";
    private static String dbFile         = "dbDump.xml";
    private static String driverClass    = "org.h2.Driver";
    private static String jdbcConnection = "jdbc:h2:file:./build/h2db/db/jhipster";
    private static String username = "jhipster";
    private static String password = null;


    public static void fullDatabaseExport(File file) throws ClassNotFoundException,
        DatabaseUnitException,
        DataSetException,
        IOException,
        SQLException {
        IDatabaseConnection connection = getConnection();

        ITableFilter filter = new DatabaseSequenceFilter(connection);
        IDataSet dataset    = new FilteredDataSet(filter, connection.createDataSet());
        FlatXmlDataSet.write(dataset, new FileOutputStream(file));
    }

    public static void fullDatabaseImport(File file) throws ClassNotFoundException,
        DatabaseUnitException,
        IOException,
        SQLException {
        IDatabaseConnection connection = getConnection();
        IDataSet dataSet = new FlatXmlDataSet(file, true);

        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
    }

    public static IDatabaseConnection getConnection() throws ClassNotFoundException,
        DatabaseUnitException,
        SQLException {
        // database connection
        Class driverClass = Class.forName(DbUnitExporter.driverClass);
        Connection jdbcConnection = DriverManager.getConnection(DbUnitExporter.jdbcConnection, DbUnitExporter.username, DbUnitExporter.password);
        return new DatabaseConnection(jdbcConnection);
    }
}
