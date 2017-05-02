package mitsai.etl.transform.feature.jFeatureHandler;

import de.lmu.ifi.dbs.jfeaturelib.features.Haralick;
import ij.process.ImageProcessor;
import mitsai.etl.transform.feature.commun.FeatureExtractor;

import java.util.Collections;
import java.util.List;

/**
 * Created by junior on 12/14/16.
 */
class HaralickImageExtractor
        implements FeatureExtractor<ImageProcessor, List<double[]>> {

    private final Haralick haralick;

    public HaralickImageExtractor() {
        haralick = new Haralick();
    }

    @Override
    public List<double[]> extractFeature(ImageProcessor image) {
        if (image == null)
            return Collections.emptyList();

        haralick.run(image);
        return haralick.getFeatures();
    }
}
