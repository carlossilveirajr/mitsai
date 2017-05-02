package mitsai.etl.transform;


import mitsai.etl.commun.Configuration;
import mitsai.etl.transform.discretizer.Discretizer;
import mitsai.etl.transform.discretizer.omega.Omega;
import mitsai.etl.transform.feature.Extractor;
import mitsai.etl.transform.feature.jFeatureHandler.HaralickExtractor;
import mitsai.etl.transform.feature.jFeatureHandler.HistogramExtractor;
import mitsai.etl.transform.feature.jFeatureHandler.SURFExtractor;
import mitsai.etl.transform.feature.jFeatureHandler.NoConversionRequired;

import java.io.IOException;
import java.util.List;

/**
 * Created by junior on 8/30/16.
 */
public class Transform extends TransformProcess {

    public Transform() throws IOException {
        Configuration conf = Configuration.getInstance();

        createProcess(conf.getConfiguration("feature_extractor"),
                conf.getConfiguration("discretizer"));
    }

    private void createProcess(String featureExtractor, String discretizer) {
        setResource(selectFeatureExtractor(featureExtractor),
                selectDiscretizer(discretizer));
    }

    private Extractor<List<double[]>> selectFeatureExtractor(String algorithm) {
        switch (algorithm) {
            case "SURF":
                return new SURFExtractor(new NoConversionRequired());
            case "HISTOGRAM":
                return new HistogramExtractor(new NoConversionRequired());
            case "HARALICK":
                return new HaralickExtractor(new NoConversionRequired());
            case "NONE":
            default:
                return null;
        }
    }

    private Discretizer selectDiscretizer(String algorithm) {
        switch (algorithm) {
            case "OMEGA":
                return new Omega();
            case "NONE":
            default:
                return null;
        }
    }
}
