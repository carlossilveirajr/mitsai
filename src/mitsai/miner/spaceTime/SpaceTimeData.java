package mitsai.miner.spaceTime;

import javafx.util.Pair;
import mitsai.miner.Item;
import mitsai.miner.apriori.Itemset;
import mitsai.miner.dataLoader.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by junior on 2/17/17.
 */
public class SpaceTimeData implements Data {
    private final int deltaSpace;
    private final Data data;
    private final int deltaTime;
    private int size = -1;
    private final HashMap<Itemset, Double> spaceDistance;
    private final HashMap<Pair<Itemset, Itemset>, Double> timeDistance;

    public SpaceTimeData(Data data, int deltaSpace, int deltaTime) {
        this.data = data;
        this.deltaSpace = deltaSpace;
        this.deltaTime = deltaTime;
        spaceDistance = new HashMap<>();
        timeDistance = new HashMap<>();
    }

    @Override
    public Set<Item> getItems() {
        return data.getItems();
    }

    @Override
    public int getSize() {
        if (size == -1)
            updateSize();
        return size;
    }

    private void updateSize() {
        Set<LocalDate> dates = new HashSet<>();
        for (Item i : data.getItems())
            dates.add(i.getTime());

        size = dates.size();
    }

    @Override
    public double countOccurrencesOf(Itemset itemset) {
        return (long) getOccurrences(itemset).size();
    }

    private Set<Item> getOccurrences(Itemset itemset) {
        final Set<Item> items = itemset.getItems();
        final Item reference = items.toArray(new Item[0])[0];

        Set<Item> occurrences = data.getItems().stream().filter(
                x -> x.equals(reference)
        ).collect(Collectors.toSet());

        spaceDistance.put(itemset, 0.0);
        for (Item i : items)
            occurrences = getItems(occurrences, i, itemset);

        spaceDistance.put(itemset, calculateSpaceDistance(itemset, occurrences));

        return occurrences;
    }

    private double calculateSpaceDistance(Itemset itemset, Set<Item> occurrences) {
        return (spaceDistance.get(itemset) / (double) occurrences.size()) % deltaSpace;
    }

    private Set<Item> getItems(Set<Item> occurrences, Item item, Itemset itemset) {
        final Set<LocalDate> dates = new HashSet<>();
        final Set<Pair<Integer, Integer>> location = new HashSet<>();

        getItems().stream()
                .filter(x -> x.equals(item))
                .forEach(x -> {
                    dates.add(x.getTime());
                    location.add(x.getLocation());
                });

        return occurrences.stream()
                .filter(x -> dates.contains(x.getTime()))
                .filter(x -> closeTo(location, x, itemset))
                .collect(Collectors.toSet());
    }

    private boolean closeTo(Set<Pair<Integer, Integer>> locations, Item item, Itemset itemset) {
        for (Pair<Integer, Integer> location : locations) {
            final double distance = distance(location, item.getLocation());
            if (distance < deltaSpace) {
                spaceDistance.put(itemset, spaceDistance.get(itemset) + distance);
                return true;
            }
        }
        return false;
    }

    private double distance(Pair<Integer, Integer> p, Pair<Integer, Integer> q) {
        return Math.sqrt(Math.pow(p.getKey() - q.getKey(), 2)
                - Math.pow(p.getValue() - q.getValue(), 2));
    }

    @Override
    public double countOccurrencesOf(Itemset antecedent, Itemset consequent) {
        Set<Item> antOcc = getOccurrences(antecedent);
        Set<Item> conOcc = getOccurrences(consequent);

        Pair<Itemset, Itemset> p = new Pair<>(antecedent, consequent);
        timeDistance.put(p, 0.0);

        long cont = antOcc.stream().filter(x -> temporalCloseTo(x.getTime(), conOcc, p))
                .filter(x -> sameId(x.getId(), conOcc)).count();

        timeDistance.put(p, (timeDistance.get(p) / (double) cont) % deltaTime);

        return cont / (double) antOcc.size();
    }

    private boolean temporalCloseTo(LocalDate time, Set<Item> conOcc, Pair<Itemset, Itemset> p) {
        for (Item i : conOcc) {
            final int deltaDays = i.getTime().compareTo(time.plusDays(deltaTime + 1));
            if (deltaDays > 1 && deltaDays <= this.deltaTime) {
                timeDistance.put(p, timeDistance.get(p) + deltaDays);
                return true;
            }
        }

        return false;
    }

    private boolean sameId(Integer id, Set<Item> conOcc) {
        return conOcc.stream().anyMatch(x -> x.getId().equals(id));
    }

    @Override
    public Double temporalDistance(Itemset antecedent, Itemset consequent) {
        return timeDistance.get(new Pair<>(antecedent, consequent));
    }

    @Override
    public Double spaceDistance(Itemset itemset) {
        return spaceDistance.get(itemset);
    }
}
