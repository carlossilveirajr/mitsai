package mitsai.etl.transform.feature.jFeatureHandler;

import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * Created by junior on 12/14/16.
 */
public class HaralickImageExtractorTest {
    private HaralickImageExtractor extractor;
    private List<double[]> features;
    private static ImageProcessor imageEg;

    @BeforeClass
    public static void init() {
        imageEg = new ColorProcessor(100, 100);
        imageEg.setColor(Color.black);
        imageEg.fill();
        imageEg.setColor(Color.white);
        imageEg.fillOval(30, 30, 40, 40);
    }

    @Before
    public void setUp() {
        extractor = new HaralickImageExtractor();
    }

    @Test
    public void
    passNullObject_returnsEmptyVectorOfFeatures() throws Exception {
        features = extractor.extractFeature(null);
        assertThat(features.isEmpty(), is(true));
    }

    @Test public void
    emptyImage_returnsOneVectorOfFeatures() throws Exception {
        features = extractor.extractFeature(new ColorProcessor(100, 100));

        assertThat(features.size(), is(1));
    }

    @Test public void
    extractFeature_returnsVectorOfFeatures() throws Exception {
        features = extractor.extractFeature(imageEg);

        assertThat(features.size(), is(1));
        assertThat(features.get(0).length, is(14));
    }
}
