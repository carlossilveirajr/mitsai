package mitsai.etl.commun;

import java.util.HashMap;
import java.util.List;

/**
 * Created by junior on 8/30/16.
 */
public interface Item {
    /**
     * In a domain with not images, this method can have a
     * dame implementation returning empty path ("").
     * Otherwise, it must returns item's image absolute path.
     *
     * @return Image Path
     */
    String getImagePath();

    /**
     * In a domain with not images, this method can have a
     * dame implementation doing nothing.
     * Otherwise, it must add item's image feature.
     *
     * @param features List of feature that are not discrete
     */
    void addFeature(double[] features);

    /**
     * In a domain with not images, this method can have a
     * dame implementation doing nothing.
     * Otherwise, it must add item's image feature.
     *
     * A dame implementation can be used if there is not
     * discretization process.
     *
     * @param features List of features that are discrete
     */
    void addDiscretedFeature(List<String> features);

    /**
     * In a domain with not images, this method can have a
     * dame implementation returns nothing.
     * Otherwise, it must add item's image feature.
     *
     * A dame implementation can be used if there is not
     * discretization process.
     *
     * @return feature list
     */
    List<double[]> getFeatureList();

    /**
     * @return number of hash (from the item splitting)
     */
    int size();

    /**
     * To split the item.
     * @param index split index
     * @return hash for the split item at index.
     */
    HashMap<String, String> hashFor(int index);
}
