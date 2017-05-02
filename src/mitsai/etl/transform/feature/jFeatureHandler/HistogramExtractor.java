package mitsai.etl.transform.feature.jFeatureHandler;

import ij.process.ColorProcessor;
import mitsai.etl.transform.feature.FeatureConverter;
import mitsai.etl.transform.feature.commun.FeatureExtractor;
import mitsai.etl.transform.feature.commun.ImageFeatureExtractor;

import java.io.IOException;
import java.util.List;

/**
 * Created by junior on 12/12/16.
 */
public class HistogramExtractor<ExpectedFormat>
        extends ImageFeatureExtractor<ColorProcessor, List<double[]>, ExpectedFormat> {

    public HistogramExtractor(FeatureConverter<List<double[]>, ExpectedFormat> converter) {
        super(converter);
    }

    @Override
    protected ColorProcessor loadImage(String imagePath) throws IOException {
        return ImageLoader.open(imagePath);
    }

    @Override
    protected FeatureExtractor setExtractor() {
        return new HistogramImageExtractor();
    }
}
