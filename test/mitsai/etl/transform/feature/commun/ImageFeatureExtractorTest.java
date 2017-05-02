package mitsai.etl.transform.feature.commun;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by junior on 11/23/15.
 */
public class ImageFeatureExtractorTest {

    private static final String PATH = "path";

    @Test public void
    passingAPath_loadImageReceives() throws Exception {
        ImageFeatureExtractor image = new TestableImageFeatureExtractor();
        image.getFeatures(PATH);
    }

    @Test public void
    imageLoadReceivesDifferentPath_theExceptionIsHandled() throws Exception {
        ImageFeatureExtractor image = new TestableImageFeatureExtractor();
        image.getFeatures(PATH + "different thing");
    }

    @Test public void
    imageIsLoad_resultsInOk() throws Exception {
        ImageFeatureExtractor image = new TestableImageFeatureExtractor();
        String result = (String) image.getFeatures(PATH);
        assertThat(result, is("ok"));
    }

    @Test public void
    imageIsNotLoad_resultsInNotOk() throws Exception {
        ImageFeatureExtractor image = new TestableImageFeatureExtractor();
        String result = (String) image.getFeatures(PATH + "different");
        assertThat(result, is("nok"));
    }

    private class TestableImageFeatureExtractor extends ImageFeatureExtractor {
        public TestableImageFeatureExtractor() {
            super(feature -> {
                String convertedFeature = (String) feature;
                return convertedFeature.equals("Feature") ? "ok" : "nok";
            });
        }

        @Override
        protected Object loadImage(String imagePath) throws IOException {
            if (!imagePath.equals(PATH))
                throw new IOException("wrong path");
            return "image";
        }

        @Override
        protected FeatureExtractor setExtractor() {
            return image -> {
                String convertedImage = (image != null) ? (String) image : "";
                if (convertedImage.equals("image"))
                    return "Feature";
                return "";
            };
        }
    }
}
