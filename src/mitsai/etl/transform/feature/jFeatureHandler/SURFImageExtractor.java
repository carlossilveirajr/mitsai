package mitsai.etl.transform.feature.jFeatureHandler;

import de.lmu.ifi.dbs.jfeaturelib.features.SURF;
import ij.process.ImageProcessor;
import mitsai.etl.transform.feature.commun.FeatureExtractor;

import java.util.Collections;
import java.util.List;

/**
 * Created by junior on 11/14/15.
 */
class SURFImageExtractor
        implements FeatureExtractor<ImageProcessor, List<double[]>> {
    private final SURF surf;

    public SURFImageExtractor() {
        surf = new SURF();
    }

    public List<double[]> extractFeature(ImageProcessor image) {
        if (image == null)
            return Collections.emptyList();

        surf.run(image);
        return surf.getFeatures();
    }
}
