package mitsai.etl.transform.feature.jFeatureHandler;

import mitsai.etl.transform.feature.FeatureConverter;

import java.util.List;

/**
 * Created by junior on 11/14/15.
 */

public class NoConversionRequired implements FeatureConverter<List<double[]>, List<double[]>> {
    public List<double[]> make(List<double[]> extractedFeatures) {
        return extractedFeatures;
    }
}
