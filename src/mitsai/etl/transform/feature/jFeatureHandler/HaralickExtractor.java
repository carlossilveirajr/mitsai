package mitsai.etl.transform.feature.jFeatureHandler;

import ij.process.ImageProcessor;
import mitsai.etl.transform.feature.FeatureConverter;
import mitsai.etl.transform.feature.commun.FeatureExtractor;
import mitsai.etl.transform.feature.commun.ImageFeatureExtractor;

import java.io.IOException;
import java.util.List;

/**
 * Created by junior on 12/14/16.
 */
public class HaralickExtractor<ExpectedFormat>
        extends ImageFeatureExtractor<ImageProcessor, List<double[]>, ExpectedFormat> {

    public HaralickExtractor(FeatureConverter converter) {
        super(converter);
    }

    @Override
    protected ImageProcessor loadImage(String imagePath) throws IOException {
        return ImageLoader.open(imagePath);
    }

    @Override
    protected FeatureExtractor<ImageProcessor, List<double[]>> setExtractor() {
        return new HaralickImageExtractor();
    }
}
