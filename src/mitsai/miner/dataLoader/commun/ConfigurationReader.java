package mitsai.miner.dataLoader.commun;

import mitsai.common.PropertiesReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by junior on 10/19/15.
 */
public class ConfigurationReader extends PropertiesReader {
    private static final String PROPERTIES_FILE = "miner.property";
    private static final String [] fields = {"database", "address", "input", "output"};

    private static ConfigurationReader instance = null;
    private final Map<String, String> values = new HashMap<>();

    public static ConfigurationReader getInstance() {
        if (instance == null)
            instance = new ConfigurationReader();

        return instance;
    }

    private ConfigurationReader() {
        super(PROPERTIES_FILE);
        try {
            super.read();
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }
    }

    @Override
    protected void extractPropertiesValues(Properties prop) {
        for (String s : fields)
            values.put(s, prop.getProperty(s));
    }

    public String getValue(String key) {
        return values.containsKey(key)? values.get(key) : "";
    }

    public String getDatabase() {
        return values.get(fields[0]);
    }
}
