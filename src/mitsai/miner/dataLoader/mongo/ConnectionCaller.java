package mitsai.miner.dataLoader.mongo;

import com.mongodb.DB;
import mitsai.common.PropertiesReader;
import mitsai.miner.dataLoader.commun.ConfigurationReader;
import org.jetbrains.annotations.NotNull;

import java.net.UnknownHostException;

/**
 * Created by junior on 10/20/15.
 */
class ConnectionCaller {
    private static ConnectionCaller instance = null;
    private DB dbReader = null;
    private Connection mongoReader = null;
    private DB dbWriter = null;

    private ConnectionCaller() throws UnknownHostException {
        PropertiesParser dbProperties = new PropertiesParser(ConfigurationReader.getInstance());
        mongoReader = new Connection(dbProperties.host,
                dbProperties.port, dbProperties.inputDatabaseName);
        dbReader = mongoReader.get();

        Connection mongoWriter = new Connection(dbProperties.host, dbProperties.port, dbProperties.outputDatabaseName);
        dbWriter = mongoWriter.get();
    }

    public static ConnectionCaller getInstance() throws UnknownHostException {
        if (instance == null)
            instance = new ConnectionCaller();

        return instance;
    }

    public DB getReaderDatabase() {
        return dbReader;
    }

    public DB getWriteDatabase() {
        return dbWriter;
    }

    public static void close() {
        if (instance != null)
            instance.finish();
    }

    private void finish() {
        mongoReader.close();
        instance = null;
    }

    public String getReaderCollection() {
        return PropertiesParser.inputCollectionName;
    }

    public String getWriterCollection() {
        return PropertiesParser.outputCollectionName;
    }

    static class PropertiesParser {
        public final String host;
        public final Integer port;
        public final String inputDatabaseName;
        public static String inputCollectionName = "";
        private final ConfigurationReader property;
        public static String outputCollectionName;
        public final String outputDatabaseName;

        public PropertiesParser(ConfigurationReader instance) {
            property = instance;

            String[] s = getStrings("address", "address (host:port)");
            host = s[0];
            port = Integer.valueOf(s[1]);

            s = getStrings("input", "input (database:collection)");
            inputDatabaseName = s[0];
            inputCollectionName = s[1];

            s = getStrings("output", "output (database:collection)");
            outputDatabaseName = s[0];
            outputCollectionName = s[1];
        }

        @NotNull
        private String[] getStrings(String address, String field) {
            String[] s = property.getValue(address).split(":");
            if (s.length != 2)
                throw new PropertiesReader.BadConfigurationFormatException(field);
            return s;
        }
    }
}
