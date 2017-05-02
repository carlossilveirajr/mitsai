package mitsai.etl.extract;

import mitsai.etl.extract.reader.ReaderFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by junior on 9/6/16.
 */
public class ExtractTest {
    private static Extract extractor;

    @BeforeClass
    public static void setUp() {
        extractor = new Extract();
    }

    @Test(expected = Extract.NoSetPathException.class) public void
    nullPath_throwsException() throws Exception {
        extractor.setPath(null, null);
    }

    @Test(expected = Extract.NoSetPathException.class) public void
    emptyPath_throwsException() throws Exception {
        extractor.setPath("", null);
    }

    @Test public void
    defaultFormatFile_json() throws Exception {
        assertThat(Extract.DEFAULT_EXTENSION, is("json"));
    }

    @Test public void
    nonEmptyPathWithoutExtension_usesJSonFormat() throws Exception {
        extractor.setPath("anyPath", getConverter());
    }

    @Test(expected = ReaderFactory.NotSupportedInputFileFormatException
            .class) public void
    nonSupportedFormat_mustThrowsExceptionButFromReaderFactory()
            throws Exception {
        extractor.setPath("anyPath.itIsNotSupportedFormatForFile", getConverter());
    }

    @NotNull
    private InputToItem getConverter() {
        return object -> null;
    }

    @Test(expected = Extract.NoSetPathException.class) public void
    callRunBeforeSet_throwsException() throws Exception {
        extractor.run();
    }
}
