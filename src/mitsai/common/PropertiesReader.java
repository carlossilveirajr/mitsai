package mitsai.common;

import java.io.*;
import java.util.Properties;

/**
 * Created by junior on 10/19/15.
 */
public abstract class PropertiesReader {

    private final String fileName;

    public PropertiesReader(String propertiesName) {
        fileName = propertiesName;
    }

    public void read() throws IOException {
        InputStream i = openPropertiesFile();
        extractPropertiesValues(createPropertiesEntity(i));
        i.close();
    }

    private Properties createPropertiesEntity(InputStream propertiesFileInStream) throws IOException {
        Properties prop = new Properties();
        if (propertiesFileInStream != null)
            prop.load(propertiesFileInStream);
        else
            throw new FileNotFoundException("Property file '" + fileName +
                    "' not found in the classpath");
        return prop;
    }

    private InputStream openPropertiesFile() throws FileNotFoundException {
        return new FileInputStream(fileName);
    }

    protected abstract void extractPropertiesValues(Properties prop);

    public static class BadConfigurationFormatException extends RuntimeException {
        public BadConfigurationFormatException(String field) {
            super("Exception in " + field);
        }
    }
}
