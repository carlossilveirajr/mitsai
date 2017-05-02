package mitsai.etl.load.database.mariadb;

import mitsai.etl.commun.Configuration;
import mitsai.etl.load.database.Target;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by junior on 12/21/16.
 */
abstract class ConnectionHandler implements Target {
    private String host;
    private String username;
    private String password;
    protected String tableName;
    private Connection connection;
    private Statement statement;

    ConnectionHandler() throws IOException, ClassNotFoundException, SQLException {
        obtainDatabaseInformationFromProperty();

        this.statement = createStatement();
    }

    protected void obtainDatabaseInformationFromProperty() throws IOException {
        host = Configuration.getInstance().getConfiguration("target_address");

        String s[] = Configuration.getInstance().getConfiguration("target_name").split(":");
        if (s.length != 3)
            throw new Configuration
                    .BadConfigurationFormatException("target_name (username:password:targetTableName)");
        username = s[0];
        password = s[1];
        tableName = s[2];
    }

    protected Statement createStatement() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(host, username, password);
        return connection.createStatement();
    }

    @Override
    public void close() {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(HashMap<String, String> toInsert) {
        try {
            if (!toInsert.isEmpty())
                statement.execute(insertIntoDatabase(toInsert));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected abstract String insertIntoDatabase(HashMap<String, String> toInsert);
}
