package mitsai.etl.transform.feature;

/**
 * Created by junior on 11/15/15.
 */
public interface Extractor<GoalFormat> {
    GoalFormat getFeatures(String imagePath);
}
