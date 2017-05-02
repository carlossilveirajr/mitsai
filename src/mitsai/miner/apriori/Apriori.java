package mitsai.miner.apriori;

import mitsai.miner.dataLoader.Data;
import mitsai.miner.Item;
import mitsai.miner.Miner;

import java.util.*;

/**
 * Created by junior on 1/18/15.
 */
public class Apriori implements Miner {
    private final SupportAndConfidence supportAndConfidence;
    private final Data database;

    private double minimumSupport;
    private double minimumConfidence;

    private Set<Itemset> seeds;
    private Set<Itemset> itemsets;

    private Set<Rule> rules;

    public Apriori(Data data) {
        supportAndConfidence = new SupportAndConfidence(data);
        database = data;
    }

    public void run(double minimumSupport, double minimumConfidence) {
        initExecution();

        setParameters(minimumSupport, minimumConfidence);
        checkParameters();
        
        createSeedsFromItems(database.getItems());
        createItemsets();

        createRules();
    }

    private void initExecution() {
        seeds = new HashSet<>();
        itemsets = new HashSet<>();
    }

    private void setParameters(double minimumSupport, double minimumConfidence) {
        this.minimumSupport = minimumSupport;
        this.minimumConfidence = minimumConfidence;
    }

    private void checkParameters() {
        if (database == null)
            throw new NullDatabaseLoader();

        if (minimumConfidence <= 0)
            throw new InvalidInputCondition("Minimum Confidence");

        if (minimumSupport <= 0)
            throw new InvalidInputCondition("Minimum Support");
    }

    private void createSeedsFromItems(Set<Item> items) {
        if (items == null)
            return;

        for (Item item : items) {
            Itemset itemset = new Itemset(item);
            if (isFrequentItemset(itemset))
                seeds.add(itemset);
        }
    }

    private boolean isFrequentItemset(Itemset itemset) {
        return supportAndConfidence.calculateSupport(itemset) >= minimumSupport;
    }

    private void createItemsets() {
        itemsets.addAll(seeds);
        Set<Itemset> Fn_1 = itemsets;

        do {
            Fn_1 = generateNextFrequentItemsetBasedOnFN(Fn_1);
            itemsets.addAll(Fn_1);
        } while (!Fn_1.isEmpty());
    }

    private Set<Itemset> generateNextFrequentItemsetBasedOnFN(Set<Itemset> fn) {
        Set<Itemset> fn_1 = new HashSet<>();

        for (Itemset i : fn)
            for (Itemset j : seeds) {
                Itemset merged = i.merge(j);
                if (isFrequentItemset(merged) && !itemsets.contains(merged))
                    fn_1.add(merged);
            }

        return fn_1;
    }

    private void createRules() {
        rules = new HashSet<>();

        for (Itemset i : itemsets)
            for (Itemset j : itemsets)
                if (!i.equals(j))
                    addRuleIfItIsFrequent(i, j);
    }

    private void addRuleIfItIsFrequent(Itemset i, Itemset j) {
        Rule rule = new Rule(i, j,
                (supportAndConfidence.calculateSupport(i) + supportAndConfidence.calculateSupport(j) / 2.0),
                supportAndConfidence.calculateConfidence(i, j),
                supportAndConfidence.calculateTemporalDistance(i,j),
                supportAndConfidence.calculateSpaceDistance(i));

        if (rule.getConfidence() >= minimumConfidence && !rules.contains(rule))
            rules.add(rule);
    }

    public List<Rule> getRules() {
        List<Rule> list = new ArrayList<>();
        list.addAll(rules);
        return list;
    }
}
