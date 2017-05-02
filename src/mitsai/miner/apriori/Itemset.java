package mitsai.miner.apriori;

import mitsai.miner.Item;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by junior on 1/18/15.
 */
public class Itemset {

    private final Set<Item> items = new HashSet<>();
    private int hash = 0;

    public Itemset() { }

    public Itemset(Item ... elements) {
        Collections.addAll(items, elements);
        updateHash();
    }

    private void updateHash() {
        hash = 0;
        for (Item i : items)
            hash += i.getValue().hashCode();
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() { return items.isEmpty(); }

    public void addItem(Item element) {
        items.add(element);
        updateHash();
    }

    public Itemset merge(Itemset itemset) {
        Itemset merged = new Itemset();
        for (Item item : items)
            merged.addItem(item);
        for (Item item : itemset.items)
            merged.addItem(item);

        return merged;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Itemset))
            return false;
        if (other == this)
            return true;

        if (((Itemset)other).size() != this.size())
            return false;

        for (Item i : ((Itemset)other).items)
            for (Item j : this.items)
                if (i.getValue().equals(j.getValue()))
                    return true;

        return false;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public String toString() {
        String output = "";

        for (Item i : items)
            output += i + " ";

        return "<" + output.trim() + ">";
    }

    public Set<Item> getItems() {
        return items;
    }
}
