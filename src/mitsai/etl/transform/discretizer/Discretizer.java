package mitsai.etl.transform.discretizer;

/**
 * Created by junior on 8/30/16.
 */
public interface Discretizer {
    void set(AttributeList list);
    String getInterval(double value);
}
