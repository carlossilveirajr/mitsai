package mitsai.miner.apriori;

import mitsai.miner.Item;
import mitsai.miner.dataLoader.Data;
import mitsai.miner.mockes.TestableItem;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ecaosir on 2/9/2016.
 */
public class ConfidenceTest {
    private static final Itemset ANTECEDENT = new Itemset(new TestableItem("antecedent"));
    private static final Itemset CONSEQUENT = new Itemset(new TestableItem("consequent"));

    private int databaseSize = 1;
    private int antecedentOccurrences = 0;
    private int consequentOccurrence = 0;
    private SupportAndConfidence confidence;

    @Before
    public void setUp() throws Exception {
        confidence = new SupportAndConfidence(new DataMock());
    }

    @Test public void
    nonExistentAntecedent_zeroConfidence() throws Exception {
        setDatabaseState(0, 1, 1);
        assertThat(confidence.calculateConfidence(ANTECEDENT, CONSEQUENT), is(0.0));
    }

    private void setDatabaseState(int occurrenceOfAntecedent, int occurrenceOfConsequent,
                                  int sizeOfDatabase) {
        antecedentOccurrences = occurrenceOfAntecedent;
        consequentOccurrence = occurrenceOfConsequent;
        databaseSize = sizeOfDatabase;
    }

    @Test public void
    nonExistentConsequent_zeroConfidence() throws Exception {
        setDatabaseState(1, 0, 1);
        assertThat(confidence.calculateConfidence(ANTECEDENT, CONSEQUENT), is(0.0));
    }

    @Test public void
    existentItemsets_returnConfidence() throws Exception {
        setDatabaseState(1, 1, 2);
        assertThat(confidence.calculateConfidence(ANTECEDENT, CONSEQUENT), is(1.0));
    }

    class DataMock implements Data {
        public double countOccurrencesOf(Itemset itemset) {
            return itemset.equals(ANTECEDENT)
                    ? antecedentOccurrences
                    : consequentOccurrence;
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
