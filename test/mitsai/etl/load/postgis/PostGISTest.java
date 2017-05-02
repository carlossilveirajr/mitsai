package mitsai.etl.load.postgis;

import mitsai.etl.load.database.postgis.PostGIS;
import mitsai.etl.load.helper.StatementMock;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by junior on 12/21/16.
 */
public class PostGISTest {
    private static final HashMap<String, String> HASH_MAP_WITH_TWO_ELEMENTS = new HashMap<String, String>() {{
        put("key1", "value1.1");
        put("key2", "valeu2.1");
    }};
    private PostGIS database;
    private StatementMock statementTestable;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException, IOException {
        database = new PostGISTestable();
    }

    @Test
    public void
    createWithoutInsert_shouldCreateTheTable() throws Exception {
        database.insert(new HashMap<>());

        assertThat(statementTestable.getSql(), is(""));
    }

    @Test
    public void
    createAndInsert_shouldCreateOnlyOneTime() throws Exception {
        database.insert(HASH_MAP_WITH_TWO_ELEMENTS);

        assertThat(statementTestable.getSql(), is(
                "CREATE TABLE IF NOT EXISTS table(key1 TEXT,key2 TEXT);" +
                "INSERT INTO table VALUES ('value1.1', 'valeu2.1');"));
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
                "CREATE TABLE IF NOT EXISTS table(key1 TEXT,key2 TEXT);" +
                        "INSERT INTO table VALUES ('value1.1', 'valeu2.1');" +
                        "INSERT INTO table VALUES ('value1.2', 'valeu2.2');"));
    }

    private class PostGISTestable extends PostGIS {
        public PostGISTestable() throws IOException, ClassNotFoundException, SQLException {}

        @Override
        protected void obtainDatabaseInformationFromProperty() throws IOException {
            tableName = "table";
        }

        @Override
        protected Statement createStatement() throws ClassNotFoundException, SQLException {
            statementTestable = new StatementMock();
            return statementTestable;
        }
    }
}
