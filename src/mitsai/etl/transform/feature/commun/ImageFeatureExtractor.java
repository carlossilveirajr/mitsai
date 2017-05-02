package mitsai.etl.transform.feature.commun;

import mitsai.etl.transform.feature.Extractor;
import mitsai.etl.transform.feature.FeatureConverter;

import java.io.IOException;

/**
 * Created by junior on 9/10/15.
 */
public abstract class ImageFeatureExtractor<LoadFormat, ExtractorOutputFormat, GoalFormat>
        implements Extractor<GoalFormat> {
    private final FeatureConverter<ExtractorOutputFormat, GoalFormat> converter;

    public ImageFeatureExtractor(FeatureConverter converter) {
        this.converter = converter;
    }

    public GoalFormat getFeatures(String imagePath) {
        FeatureExtractor<LoadFormat, ExtractorOutputFormat> extractor = setExtractor();
        LoadFormat image = null;

        try {
            image = loadImage(imagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return converter.make(extractor.extractFeature(image));
    }

    protected abstract LoadFormat loadImage(String imagePath) throws IOException;

    protected abstract FeatureExtractor<LoadFormat, ExtractorOutputFormat> setExtractor();
}
