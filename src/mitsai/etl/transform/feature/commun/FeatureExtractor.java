package mitsai.etl.transform.feature.commun;

/**
 * Created by junior on 11/14/15.
 */
public interface FeatureExtractor<InputFormat, OutputFormat> {
    OutputFormat extractFeature(InputFormat image);
}
