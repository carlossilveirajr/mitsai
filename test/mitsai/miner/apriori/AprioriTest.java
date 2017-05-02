package mitsai.miner.apriori;

import mitsai.miner.Item;
import mitsai.miner.mockes.TestableItem;
import org.junit.Before;
import org.junit.Test;
import mitsai.miner.dataLoader.Data;
import mitsai.miner.Miner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by junior on 1/18/15.
 */
public class AprioriTest {
    private Miner miner;
    private DatabaseMock database;
    private final double minimumSupport = .2;
    private final double minimumConfidence = .2;

    @Before
    public void setUp() {
        database =  new DatabaseMock();
        database.makeDatabaseWithItems(0);

        miner = new Apriori(database);
    }


    @Test(expected = Miner.NullDatabaseLoader.class)
    public void runAprioriWithNullDatabase_shouldThrowAnException() throws Exception {
        miner = new Apriori(null);
        miner.run(minimumSupport, minimumConfidence);
    }

    @Test(expected = Miner.InvalidInputCondition.class)
    public void runAprioriWithInvalidSupport_shouldThrowAnException() throws Exception {
        miner.run(-.3, minimumConfidence);
    }

    @Test(expected = Miner.InvalidInputCondition.class)
    public void runAprioriWithInvalidConfidence_shouldThrowAnException() throws Exception {
        miner.run(minimumSupport, .0);
    }

    @Test
    public void runAprioriToNoItem_shouldReturnNoRule() throws Exception {
        miner.run(minimumSupport, minimumConfidence);

        assertThat(miner.getRules(), is(Collections.EMPTY_LIST));
    }

    @Test
    public void runAprioriToOneItem_shouldReturnNoRule() throws Exception {
        database.makeDatabaseWithItems(1);

        miner = new Apriori(database);
        miner.run(minimumSupport, minimumConfidence);

        assertThat(miner.getRules(), is(Collections.EMPTY_LIST));
    }

    @Test public void runAprioriToTwoItem_returnsTwoRules() throws Exception {
        database.makeDatabaseWithItems(2);

        miner = new Apriori(database);
        miner.run(minimumSupport, minimumConfidence);

        assertThat(miner.getRules().size(), is(2));
    }
}

class DatabaseMock implements Data {
    private Set<Item> items = new HashSet<>();

    public void makeDatabaseWithItems(int size) {
        items = new HashSet<>();

        addElementsToItemsets(size);
    }

    private void addElementsToItemsets(int number) {
        for (int i = 0; i < number; ++i)
            items.add(new TestableItem("Item" + i));
    }

    public Set<Item> getItems() {
        return items;
    }

    public int getSize() {
        return items.size();
    }

    @Override
    public double countOccurrencesOf(Itemset itemset) {
        Set<Item> items2 = itemset.getItems();
        return this.items.stream().filter(items2::contains).count();
    }

    @Override
    public double countOccurrencesOf(Itemset antecedent, Itemset consequent) {
        return 0;
    }

    @Override
    public Double temporalDistance(Itemset antecedent, Itemset consequent) {
        return null;
    }

    @Override
    public Double spaceDistance(Itemset itemset) {
        return null;
    }
}
