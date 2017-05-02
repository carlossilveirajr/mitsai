package mitsai.miner.apriori;

import mitsai.miner.dataLoader.Data;

import java.util.HashMap;

/**
 * Created by ecaosir on 2/8/2016.
 */
class SupportAndConfidence {

    private final Data database;
    private final HashMap<Itemset, Double> supports = new HashMap<>();

    public SupportAndConfidence(Data database) {
        this.database = database;
    }

    public double calculateSupport(Itemset itemset) {
        if (!supports.containsKey(itemset))
            supports.put(itemset,
                        database.countOccurrencesOf(itemset) / database.getSize());

        return supports.get(itemset);
    }

    public double calculateConfidence(Itemset antecedent, Itemset consequent) {
        return database.countOccurrencesOf(antecedent, consequent);
    }

    public Double calculateTemporalDistance(Itemset i, Itemset j) {
        return database.temporalDistance(i,j);
    }

    public Double calculateSpaceDistance(Itemset i) {
        return database.spaceDistance(i);
    }
}
