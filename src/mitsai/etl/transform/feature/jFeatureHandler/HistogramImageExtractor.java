package mitsai.etl.transform.feature.jFeatureHandler;

import de.lmu.ifi.dbs.jfeaturelib.LibProperties;
import de.lmu.ifi.dbs.jfeaturelib.features.Histogram;
import ij.process.ImageProcessor;
import mitsai.etl.transform.feature.commun.FeatureExtractor;

import java.util.Collections;
import java.util.List;

/**
 * Created by junior on 12/12/16.
 */
class HistogramImageExtractor
        implements FeatureExtractor<ImageProcessor, List<double[]>> {

    private final Histogram histogram;

    public HistogramImageExtractor() {
        histogram = new Histogram();
        LibProperties properties = new LibProperties();
        properties.setProperty(LibProperties.HISTOGRAMS_TYPE, "RGB");
        properties.setProperty(LibProperties.HISTOGRAMS_BINS, 2);
        histogram.setProperties(properties);
    }

    @Override
    public List<double[]> extractFeature(ImageProcessor image) {
        if (image == null)
            return Collections.emptyList();

        histogram.run(image);
        return histogram.getFeatures();
    }

}
