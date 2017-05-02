package mitsai.miner.dataLoader.commun;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by junior on 11/4/15.
 */
public class ConfigurationReaderTest {
    @Test public void
    readingAnNotExistentValue() throws Exception {
        ConfigurationReader cr = ConfigurationReader.getInstance();
        assertThat(cr.getValue("ANYTHING"), is(""));
    }

}
