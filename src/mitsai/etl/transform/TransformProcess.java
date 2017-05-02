package mitsai.etl.transform;

import mitsai.etl.commun.Item;
import mitsai.etl.transform.feature.Extractor;
import mitsai.etl.transform.discretizer.AttributeList;
import mitsai.etl.transform.discretizer.Discretizer;

import java.util.*;

/**
 * Created by junior on 8/30/16.
 */
class TransformProcess {
    private List<Item> records = new ArrayList<>();
    private Discretizer discretizer = null;
    private Extractor<List<double[]>> extractor = null;
    private final AttributeList featuresValues = new AttributeList();

    void setResource(Extractor<List<double[]>> featureExtractor,
                     Discretizer discretizer) {
        this.extractor = featureExtractor;
        this.discretizer = discretizer;
    }

    public void setRecords(List<Item> records) {
        this.records = records;
    }

    public List<Item> getRecords() {
        return records;
    }

    public void run() {
        if (extractor != null)
            extractImageFeatures();
    }

    private void extractImageFeatures() {
        for (Item item : records) {
            List<double[]> featuresList = extractor.getFeatures(item.getImagePath());

            for (double[] features : featuresList) {
                item.addFeature(features);

                for (double value : features)
                    featuresValues.add(value, 0);
            }
        }

        if (discretizer != null)
            discretizeImageFeature();
    }

    private void discretizeImageFeature() {
        discretizer.set(featuresValues);

        for (Item item : records) {
            List<double[]> featuresList = item.getFeatureList();

            for (double[] features : featuresList)
                item.addDiscretedFeature(translateFeatures(features));
        }
    }

    private List<String> translateFeatures(double[] features) {
        List<String> intervals = new ArrayList<>();

        for(double feature : features)
            intervals.add(discretizer.getInterval(feature));

        return intervals;
    }
}
