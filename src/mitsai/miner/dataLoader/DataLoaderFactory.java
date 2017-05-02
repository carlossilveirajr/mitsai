package mitsai.miner.dataLoader;

import mitsai.miner.apriori.Rule;
import mitsai.miner.dataLoader.commun.*;
import mitsai.miner.dataLoader.mongo.MongoReader;
import mitsai.miner.dataLoader.mongo.MongoWriter;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by junior on 10/26/15.
 */
public class DataLoaderFactory {
    private LoadFactory loaderFactory;
    private WriteFactory writeFactory;
    private DatabaseInMemory database;

    public DataLoaderFactory(DataConverter converter)
            throws NoFactoryException, UnknownHostException {
        switch (getDatabaseSystemFromProperties().toUpperCase()) {
            case "MONGO":
                loaderFactory = new MongoReader(converter);
                writeFactory = new MongoWriter();
                break;
            default:
                throw new NoFactoryException();
        }

        fieldDatabaseInMemory();
    }

    private String getDatabaseSystemFromProperties() {
        return ConfigurationReader.getInstance().getDatabase();
    }

    private void fieldDatabaseInMemory() {
        try {
            database = loaderFactory.fieldDatabase();
        } catch (UnknownHostException e) {
            database = new DatabaseInMemory();
        }
    }

    public Data getDatabase() {
        return database == null ? new DatabaseInMemory() : database;
    }

    public void storeResults(List<Rule> rules) {
        for (Rule r : rules)
            writeFactory.store(r);
    }
}
