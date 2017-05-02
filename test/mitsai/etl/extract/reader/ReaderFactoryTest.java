package mitsai.etl.extract.reader;

import mitsai.etl.commun.Item;
import mitsai.etl.extract.InputToItem;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by junior on 9/5/16.
 */
public class ReaderFactoryTest {

    private static ReaderFactory factory;
    private static DummyConverter converter;

    @BeforeClass
    public static void setUp() throws Exception {
        factory = new ReaderFactory();
        converter = new DummyConverter();
    }

    @Test(expected = ReaderFactory.NotSupportedInputFileFormatException.class)
    public void
    notSupportedFormat_throwsException() throws Exception {
        factory.getReader("NotSupportedFormat", converter);
    }

    @Test(expected = ReaderFactory.NullInputToItemConverter.class)
    public void
    nullConverter_throwsException() throws Exception {
        factory.getReader("NotSupportedFormat", null);
    }

    @Test public void
    forJSONFormat_returnsJsonReader() throws Exception {
        Reader reader = factory.getReader("json", converter);

        assertTrue(reader instanceof JsonReader);
    }

    @Test public void
    forAnyTypingFormatForJSON_returnsJsonReader() throws Exception {
        Reader reader = factory.getReader("jSoN", converter);

        assertTrue(reader instanceof JsonReader);
    }

    private static class DummyConverter implements InputToItem {
        @Override
        public Item convert(Object object) {
            return null;
        }
    }
}
