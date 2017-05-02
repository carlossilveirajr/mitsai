package mitsai.etl.transform.feature;

/**
 * Created by junior on 11/14/15.
 */
public interface FeatureConverter<InputFormat, OutputFormat> {
    OutputFormat make(InputFormat extractedFeatures);
}
