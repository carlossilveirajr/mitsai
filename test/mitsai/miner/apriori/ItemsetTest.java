package mitsai.miner.apriori;

import mitsai.miner.apriori.Itemset;
import mitsai.miner.mockes.TestableItem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by junior on 1/18/15.
 */
public class ItemsetTest {

    private Itemset itemset;
    private final TestableItem element01 = new TestableItem("element01");
    private final TestableItem element02 = new TestableItem("element02");

    private void createItemsetWithTwoItems() {
        itemset.addItem(element01);
        itemset.addItem(element02);
    }

    @Before
    public void setUp() {
        itemset = new Itemset();
    }

    @Test
    public void createEmptyItemset_shouldHaveZeroSize() throws Exception {
        assertThat(itemset.size(), is(0));
        assertThat(itemset.isEmpty(), is(Boolean.TRUE));
    }

    @Test
    public void addAnElement_shouldHaveOneSize() throws Exception {
        itemset.addItem(element01);

        assertThat(itemset.size(), is(1));
        assertThat(itemset.isEmpty(), is(Boolean.FALSE));
    }

    @Test
    public void addTwiceAnElement_shouldNotChangeItSize() throws Exception {
        itemset.addItem(element01);
        itemset.addItem(element01);

        assertThat(itemset.size(), is(1));
    }

    @Test
    public void addTwoElements_shouldHaveTwoSize() throws Exception {
        createItemsetWithTwoItems();

        assertThat(itemset.size(), is(2));
    }

    @Test
    public void theSameItemset_shouldBeEqual() throws Exception {
        createItemsetWithTwoItems();

        assertThat(itemset, is(itemset));
    }

    @Test
    public void twoDifferentItemset_shouldNotBeEqual() throws Exception {
        createItemsetWithTwoItems();

        Itemset differentItemset = new Itemset();
        differentItemset.addItem(element01);

        assertThat(itemset.equals(differentItemset), is(Boolean.FALSE));
    }

    @Test
    public void twoEqualItemset_shouldBeEqual() throws Exception {
        createItemsetWithTwoItems();

        Itemset differentItemset = new Itemset();
        differentItemset.addItem(element02);
        differentItemset.addItem(element01);

        assertThat(itemset.equals(differentItemset), is(Boolean.TRUE));
    }

    @Test
    public void itemsShouldBePrinted() throws Exception {
        createItemsetWithTwoItems();

        assertThat(itemset.toString(), is("<element02 element01>"));
    }

    @Test
    public void emptyItemset_shouldPrintEmptySet() throws Exception {
        assertThat(itemset.toString(), is("<>"));
    }

    @Test
    public void createItemsetWithOneItem_shouldStoreItem() throws Exception {
        itemset = new Itemset(element01);

        assertThat(itemset.size(), is(1));
    }

    @Test
    public void createItemsetWithItems_shouldStoreItems() throws Exception {
        itemset = new Itemset(element01, element02);

        assertThat(itemset.size(), is(2));
    }

    @Test
    public void createItemsetWithEqualItems_shouldStoreOneItem() throws Exception {
        itemset = new Itemset(element01, element01, element01);

        assertThat(itemset.size(), is(1));
    }
}
