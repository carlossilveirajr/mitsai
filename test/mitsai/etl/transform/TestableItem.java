package mitsai.etl.transform;

import mitsai.etl.commun.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by junior on 9/7/16.
 */
class TestableItem implements Item {
    private final List<double[]> features = new ArrayList<>();
    private final List<List<String>> discretedFeature = new ArrayList<>();

    @Override
    public String getImagePath() {
        return null;
    }

    @Override
    public void addFeature(double[] features) {
        this.features.add(features);
    }

    @Override
    public void addDiscretedFeature(List<String> features) {
        discretedFeature.add(features);
    }

    @Override
    public List<double[]> getFeatureList() {
        return features;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public HashMap<String, String> hashFor(int index) {
        return null;
    }

    public List<List<String>> getDiscretedFeature() {
        return discretedFeature;
    }
}
