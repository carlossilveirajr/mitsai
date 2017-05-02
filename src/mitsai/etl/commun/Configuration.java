package mitsai.etl.commun;

import mitsai.common.PropertiesReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by junior on 8/30/16.
 */
public class Configuration extends PropertiesReader {
    private static final String EMPTY_DEFAULT_VALUE_FOR_EXTRACTION = "";
    private final HashMap<String, String> configurations = new HashMap<>();

    private Configuration() throws IOException {
        super("./etl.property");
        read();
    }

    @Override
    protected void extractPropertiesValues(Properties prop) {
        configurations.put("feature_extractor", prop.getProperty("feature_extractor", EMPTY_DEFAULT_VALUE_FOR_EXTRACTION));
        configurations.put("discretizer", prop.getProperty("discretizer", EMPTY_DEFAULT_VALUE_FOR_EXTRACTION));
        configurations.put("target", prop.getProperty("target", EMPTY_DEFAULT_VALUE_FOR_EXTRACTION));
        configurations.put("target_address", prop.getProperty("target_address", EMPTY_DEFAULT_VALUE_FOR_EXTRACTION));
        configurations.put("target_name", prop.getProperty("target_name", EMPTY_DEFAULT_VALUE_FOR_EXTRACTION));
    }

    public String getConfiguration(String value) {
        return configurations.containsKey(value) ?
                configurations.get(value) : EMPTY_DEFAULT_VALUE_FOR_EXTRACTION;
    }

    private static Configuration instance = null;

    public static Configuration getInstance() throws IOException {
        if (instance == null) {
            instance = new Configuration();
        }

        return instance;
    }

}
