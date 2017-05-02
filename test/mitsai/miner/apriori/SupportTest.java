package mitsai.miner.apriori;

import mitsai.miner.Item;
import mitsai.miner.dataLoader.Data;
import mitsai.miner.mockes.TestableItem;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by ecaosir on 2/8/2016.
 */
public class SupportTest {
    private static final Itemset ITEMSET = new Itemset(new TestableItem("itemset"));

    private int databaseSize = 0;
    private int itemsetOccurrences = 0;

    private SupportAndConfidence support;

    @Before
    public void setUp() {
        support = new SupportAndConfidence(new DataMock());
    }

    @Test public void
    calculateSupportOfNonExistentItemset_itIsZero() throws Exception {
        setDatabaseState(0, 1);
        assertThat(support.calculateSupport(ITEMSET), is(0.0));
    }

    private void setDatabaseState(int itemsetOccurrencesCounter, int databaseSize) {
        itemsetOccurrences = itemsetOccurrencesCounter;
        this.databaseSize = databaseSize;
    }

    @Test public void
    calculateSupportOfTheOnlyExistentItemset_itIsOne() throws Exception {
        setDatabaseState(1, 1);
        assertThat(support.calculateSupport(ITEMSET), is(1.0));
    }

    @Test public void
    calculateSupportInADatabaseWithManyItemset_itIsOnePerDatabaseSize() throws Exception {
        setDatabaseState(1, 4);
        assertThat(support.calculateSupport(ITEMSET), is(.25));
    }

    @Test public void
    calculateSupportWithManyOccurrenceAndManyItemsets_itIsOccurrencesPerDatabaseSize() throws Exception {
        setDatabaseState(3, 4);
        assertThat(support.calculateSupport(ITEMSET), is(.75));
    }

    class DataMock implements Data {
        public double countOccurrencesOf(Itemset itemset) {
            return itemsetOccurrences;
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

        public Set<Item> getItems() {
            return null;
        }

        public int getSize() {
            return databaseSize;
        }
    }
}
