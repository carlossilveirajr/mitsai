package mitsai.miner.dataLoader;

import mitsai.miner.Item;
import mitsai.miner.apriori.Itemset;

import java.util.Set;

/**
 * Created by junior on 1/18/15.
 */
public interface Data {
    Set<Item> getItems();

    int getSize();

    double countOccurrencesOf(Itemset itemset);

    double countOccurrencesOf(Itemset antecedent, Itemset consequent);

    Double temporalDistance(Itemset antecedent, Itemset consequent);

    Double spaceDistance(Itemset itemset);
}
