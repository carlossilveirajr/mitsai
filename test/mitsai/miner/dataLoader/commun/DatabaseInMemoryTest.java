package mitsai.miner.dataLoader.commun;

import mitsai.miner.mockes.TestableItem;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by junior on 10/26/15.
 */
public class DatabaseInMemoryTest {
    private DatabaseInMemory data;

    private void insertItemInDatabase(int number) {
        for (; number > 0; --number)
            data.set(new TestableItem("item" + number));
    }

    @Before
    public void setUp() throws Exception {
        data = new DatabaseInMemory();
    }

    @Test public void
    insertAnItem_databaseWithSizeOne() throws Exception {
        insertItemInDatabase(1);
        assertThat(data.getSize(), is(1));
    }

    @Test public void
    insertTwoItem_databaseWithSizeTwo() throws Exception {
        insertItemInDatabase(2);
        assertThat(data.getSize(), is(2));
    }

    @Test public void
    insertSameItem_noChangeInDatabaseSize() throws Exception {
        insertItemInDatabase(2);
        insertItemInDatabase(2);
        assertThat(data.getSize(), is(2));
    }
}
