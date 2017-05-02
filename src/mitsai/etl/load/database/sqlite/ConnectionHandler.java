package mitsai.etl.load.database.sqlite;

import mitsai.etl.commun.Configuration;
import mitsai.etl.load.database.Target;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by junior on 12/18/16.
 */
@SuppressWarnings("ThrowablePrintedToSystemOut")
abstract class ConnectionHandler implements Target {
    private static final int TIME_TO_LIVE = 30;
    private java.sql.Connection connection = null;
    private Statement statement;

    protected String databaseName;
    protected String tableName;

    ConnectionHandler() throws SQLException, ClassNotFoundException, IOException {
        Class.forName("org.sqlite.JDBC");

        obtainDatabaseInformation();

        connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
        statement = createStatement();
        statement.setQueryTimeout(TIME_TO_LIVE);
    }

    protected void obtainDatabaseInformation() throws IOException {
        databaseName = Configuration.getInstance().getConfiguration("target_address");
        tableName = Configuration.getInstance().getConfiguration("target_name");
    }

    protected Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    @Override
    public void insert(HashMap<String, String> toInsert) {
        try {
            insertData(toInsert);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            close();
        }
    }

    protected abstract void insertData(HashMap<String, String> toInsert) throws SQLException;

    void execSQL(String statementSQL) throws SQLException {
        statement.executeUpdate(statementSQL);
    }

    @Override
    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            System.err.print(e);
        }
    }
}
