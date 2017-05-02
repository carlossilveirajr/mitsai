package mitsai.etl.transform;

import mitsai.etl.commun.Item;
import mitsai.etl.transform.discretizer.AttributeList;
import mitsai.etl.transform.discretizer.Discretizer;
import mitsai.etl.transform.feature.Extractor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by junior on 9/7/16.
 */
public class TransformProcessTest {

    private TestableItem item;
    private TransformProcess process;
    private final double[] featureVector = {0.1, 0.2, 0.3};

    @Before
    public void setUp() throws Exception {
        item = new TestableItem();

        process = new TransformProcess();
        process.setRecords(createList(item));
    }

    private List<Item> createList(Item ... items) {
        List<Item> itemsList = new ArrayList<>();

        Collections.addAll(itemsList, items);

        return itemsList;
    }

    @Test public void
    runWithNoTransformation_nothingHappensToTheItem() throws Exception {
        process.run();

        assertThat(process.getRecords().get(0), is(item));
    }

    @Test public void
    runWithFeatureExtraction_transformImageInFeature() throws Exception {
        process.setResource(new ExtractorMoke(), null);

        process.run();

        assertThat(process.getRecords().get(0)
                .getFeatureList().get(0), is(featureVector));
    }

    @Test public void
    runWithCompleteProcess_returnsDiscretizeItems() throws Exception {
        process.setResource(new ExtractorMoke(), new DiscretizerMoke());

        process.run();

        assertThat(item.getDiscretedFeature().get(0).get(0),
                is("interval-" + 0.1));
    }

    private class ExtractorMoke implements Extractor<List<double[]>> {
        @Override
        public List<double[]> getFeatures(String imagePath) {
            List<double[]> out = new ArrayList<>();
            out.add(featureVector);
            return out;
        }
    }

    private class DiscretizerMoke implements Discretizer {
        @Override
        public void set(AttributeList list) {

        }

        @Override
        public String getInterval(double value) {
            return "interval-" + value;
        }
    }
}
