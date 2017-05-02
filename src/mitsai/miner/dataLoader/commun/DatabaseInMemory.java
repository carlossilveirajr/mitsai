package mitsai.miner.dataLoader.commun;

import mitsai.miner.dataLoader.Data;
import mitsai.miner.Item;
import mitsai.miner.apriori.Itemset;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by junior on 10/26/15.
 */
public class DatabaseInMemory implements Data
{
    private final Set<Item> items = new HashSet<>();

    public Set<Item> getItems() {
        return items;
    }

    public int getSize() {
        return items.size();
    }

    @Override
    public double countOccurrencesOf(Itemset itemset) {
        return 0;
    }

    @Override
    public double countOccurrencesOf(Itemset cause, Itemset consequence) {
        return 0;
    }

    @Override
    public Double temporalDistance(Itemset antecedent, Itemset consequent) {
        return 0.0;
    }

    @Override
    public Double spaceDistance(Itemset itemset) {
        return 0.0;
    }

    public void set(Item item) {
        items.add(item);
    }
}
