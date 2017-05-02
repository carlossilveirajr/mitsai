package mitsai.etl.load.sqlite;

import mitsai.etl.load.database.sqlite.SQLite;
import mitsai.etl.load.helper.StatementMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by junior on 12/18/16.
 */
public class SQLiteTest {
    private static final HashMap<String, String> HASH_MAP_WITH_TWO_ELEMENTS = new HashMap<String, String>() {{
        put("key1", "value1.1");
        put("key2", "valeu2.1");
    }};
    private SQLite database;
    private StatementMock statementTestable;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException, IOException {
        database = new SQLiteTestable();
    }

    @After
    public void clean() {
        database.close();
    }

    @Test
    public void
    createWithoutInsert_shouldCreateTheTable() throws Exception {
        database.insert(new HashMap<>());

        assertThat(statementTestable.getSql(), is("CREATE TABLE IF NOT EXISTS table ();"));
    }

    @Test
    public void
    createAndInsert_shouldCreateOnlyOneTime() throws Exception {
        database.insert(HASH_MAP_WITH_TWO_ELEMENTS);

        assertThat(statementTestable.getSql(), is("CREATE TABLE IF NOT EXISTS table (key1,key2);" +
                "INSERT INTO table VALUES('value1.1', 'valeu2.1');"));
    }

    @Test
    public void
    createAndTwoInsert_shouldCreateOnlyOneTimeWithTheFirstKeys() throws Exception {
        database.insert(HASH_MAP_WITH_TWO_ELEMENTS);
        database.insert(new HashMap<String, String>() {{
            put("key3", "value1.2");
            put("key4", "valeu2.2");
        }});

        assertThat(statementTestable.getSql(), is(
                "CREATE TABLE IF NOT EXISTS table (key1,key2);" +
                "INSERT INTO table VALUES('value1.1', 'valeu2.1');" +
                "INSERT INTO table VALUES('value1.2', 'valeu2.2');"));
    }

    class SQLiteTestable extends SQLite {

        public SQLiteTestable()
                throws SQLException, ClassNotFoundException, IOException {
        }

        @Override
        protected Statement createStatement() throws SQLException {
            statementTestable = new StatementMock();
            return statementTestable;
        }

        @Override
        protected void obtainDatabaseInformation() {
            databaseName = "sqliteTestFile.db";
            tableName = "table";
        }
    }
}
