package mitsai.miner.dataLoader.commun;

import mitsai.common.PropertiesReader;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * Created by junior on 11/4/15.
 */
public class PropertiesReaderTest {
    @Test(expected = FileNotFoundException.class) public void
    readFromNonExistentFile_throwsException() throws Exception {
        PropertiesReader property = new PropertiesReaderTestable();
        property.read();
    }

    private class PropertiesReaderTestable extends PropertiesReader {
        public PropertiesReaderTestable() {
            super("nonExistentFile");
        }
        @Override
        protected void extractPropertiesValues(Properties prop) { }
    }
}
