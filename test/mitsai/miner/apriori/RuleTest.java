package mitsai.miner.apriori;

import mitsai.miner.apriori.InvalidValue;
import mitsai.miner.apriori.Itemset;
import mitsai.miner.apriori.Rule;
import mitsai.miner.mockes.TestableItem;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by junior on 1/20/15.
 */
public class RuleTest {
    private static final Itemset EMPTY_ITEMSET = new Itemset();
    private static Itemset cause;
    private static Itemset consequence;
    private static double support;
    private static double delta;
    private static double confidence;
    private static Rule rule;

    @BeforeClass
    public static void setUp() throws Exception {
        cause = new Itemset();
        cause.addItem(new TestableItem("cause01"));
        cause.addItem(new TestableItem("cause02"));

        consequence = new Itemset();
        consequence.addItem(new TestableItem("consequence01"));
        consequence.addItem(new TestableItem("consequence02"));
        support = 0.12;
        delta = 0.00001;
        confidence = 0.7;
    }

    @Test public void
    createEmptyRule_shouldHaveEmptyCauseAndConsequence() throws Exception {
        Rule rule = new Rule();

        assertThat(rule.getCause(), is(EMPTY_ITEMSET));
        assertThat(rule.getConsequence(), is(EMPTY_ITEMSET));
    }

    private void createStandardRule() {
        rule = new Rule(cause, consequence);
    }

    @Test public void
    createRule_shouldHaveCauseAndConsequence() throws Exception {
        createStandardRule();

        assertThat(rule.getCause(), is(cause));
        assertThat(rule.getConsequence(), is(consequence));
    }

    @Test public void
    ruleShouldBePrinted() throws Exception {
        createStandardRule();

        assertThat(rule.toString(), is("<cause02 cause01> -> <consequence01 " +
                "consequence02> (0.0,0.0)"));
    }

    @Test public void
    createRuleWithSupportAndConfidence_shouldStoreTheValues() throws Exception {
        Rule rule = new Rule(cause, consequence, support, confidence);

        assertEquals(support, rule.getSupport(), delta);
        assertEquals(confidence, rule.getConfidence(), delta);
    }

    @Test(expected = InvalidValue.class) public void
    setNegativeSupport_shouldThrowAnException() throws Exception {
        createStandardRule();
        rule.adjust(-support, null);
    }

    @Test(expected = InvalidValue.class) public void
    setNegativeConfidence_shouldThrowAnException() throws Exception {
        createStandardRule();
        rule.adjust(null, -confidence);
    }

    @Test public void
    setValidSupportAndNullConfidence_shouldChangeJustSupport() throws Exception {
        createStandardRule();
        rule.adjust(support, null);

        assertEquals(support, rule.getSupport(), delta);
        assertEquals(0.0, rule.getConfidence(), delta);
    }

    @Test public void
    setValidConfidenceAndNullSupport_shouldChangeJustConfidence() throws Exception {
        createStandardRule();
        rule.adjust(null, confidence);

        assertEquals(0.0, rule.getSupport(), delta);
        assertEquals(confidence, rule.getConfidence(), delta);
    }

}
