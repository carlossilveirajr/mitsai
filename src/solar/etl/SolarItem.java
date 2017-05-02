package solar.etl;

import mitsai.etl.commun.Item;

import java.util.*;

/**
 * Created by junior on 9/7/16.
 */
class SolarItem implements Item {
    private long date = 0;
    private final List<String> locations;
    private final List<String> haleClasses;
    private final List<String> mcintoshClasses;
    private final List<String> sunspotAreas;
    private final List<Integer> numbers;

    private final List<double[]> features;
    private final List<String[]> featureDiscrete;

    private String path = "";
    private int numberOfSunspots = 0;

    public SolarItem() {
        locations = new ArrayList<>();
        sunspotAreas = new ArrayList<>();
        haleClasses = new ArrayList<>();
        mcintoshClasses = new ArrayList<>();
        features = new ArrayList<>();
        featureDiscrete = new ArrayList<>();
        numbers = new ArrayList<>();
    }

    @Override
    public String getImagePath() {
        return path;
    }

    @Override
    public void addFeature(double[] features) {
        this.features.add(features);
    }

    @Override
    public void addDiscretedFeature(List<String> features) {
        featureDiscrete.add(features.toArray(new String[0]));
    }

    @Override
    public List<double[]> getFeatureList() {
        try {
            return features.subList(0, haleClasses.size());
        } catch (IndexOutOfBoundsException e) {
            return features;
        }
    }

    @Override
    public int size() {
        return numberOfSunspots;
    }

    @Override
    public HashMap<String, String> hashFor(int index) {
        HashMap<String, String> out = new HashMap<>();

        out.put("date", Long.toString(date));
        out.put("location", locations.get(index));
        out.put("number", numbers.get(index).toString());
        out.put("haleClass", haleClasses.get(index));
        out.put("mcintoshClass", mcintoshClasses.get(index));
        out.put("area", sunspotAreas.get(index));
        out.put("feature", hashForFeature(index));
        out.put("featureDiscrete", hashForFeatureDiscrete(index));

        return out;
    }

    private String hashForFeature(int index) {
        if (features.isEmpty())
                return "";

        double[] doubles = features.get(adjustIndex(index, features.size()));
        String out = "";
        for (double d : doubles)
            out += d + " ";

        return out;
    }

    private String hashForFeatureDiscrete(int index) {
        if (featureDiscrete.isEmpty())
            return "";

        String [] s = featureDiscrete.get(adjustIndex(index, featureDiscrete.size()));
        return String.join(",", Arrays.asList(s));
    }

    private int adjustIndex(int index, int size) {
        if (size == 1)
            return 0;
        if (size < index)
            return index % size;
        return index;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setNumberOfSunspots(int numberOfSunspots) {
        this.numberOfSunspots = numberOfSunspots;
    }

    public void addHaleClass(String ... haleClasses) {
        Collections.addAll(this.haleClasses, haleClasses);
    }

    public void addMcintoshClass(String ... mcintoshClasses) {
        Collections.addAll(this.mcintoshClasses, mcintoshClasses);
    }

    public void addSunspotArea(String... areas) {
        Collections.addAll(this.sunspotAreas, areas);
    }

    public void addLocation(String... locations) {
        Collections.addAll(this.locations, locations);
    }

    public void addNumber(Integer ... numbers) {
        Collections.addAll(this.numbers, numbers);
    }
}
