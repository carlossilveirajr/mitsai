package mitsai.etl.load.database.sqlite;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by junior on 12/18/16.
 */
public class SQLite extends ConnectionHandler {
    private boolean firstInsert = true;

    public SQLite() throws SQLException, ClassNotFoundException, IOException {
        super();
    }

    @Override
    protected void insertData(HashMap<String, String> toInsert) throws SQLException {
        if (firstInsert) {
            createTableIfNotExist(toInsert.keySet());
            firstInsert = false;
        }

        insertTheDatabaseLine(toInsert);
    }

    private void createTableIfNotExist(Set<String> keys) throws SQLException {
        String fields = keys
                .stream().collect(Collectors.joining(","));

        execSQL("CREATE TABLE IF NOT EXISTS " +
                tableName + " (" + fields + ")");
    }

    private void insertTheDatabaseLine(HashMap<String, String> toInsert) throws SQLException {
        if (toInsert.isEmpty())
            return;

        String data = toInsert.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.joining("', '"));

        execSQL("INSERT INTO " + tableName + " VALUES('" +
                data + "')");
    }
}
