package mitsai.etl.load.database.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import mitsai.etl.commun.Configuration;
import mitsai.etl.load.database.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by junior on 9/4/16.
 */
public class Mongo implements Target {

    private DBCollection collection;
    private String address;
    private String port;
    private String databaseName;
    private String collectionName;

    private Connection connection;
    private List<DBObject> objs = new ArrayList<>();

    public Mongo() throws IOException {
        parseConfiguration(getConfiguration());

        connection = new Connection(address, Integer.valueOf(port), databaseName);
        collection = connection.get().getCollection(collectionName);
    }

    private void parseConfiguration(Configuration configuration) {
        String [] s = configuration.getConfiguration("target_address").split(":");
        if (s.length != 2)
            throw new Configuration.BadConfigurationFormatException("target_address (address:port)");

        address = s[0];
        port = s[1];

        s = configuration.getConfiguration("target_name").split(":");
        if (s.length != 2)
            throw new Configuration.BadConfigurationFormatException("target_name (database:collection)");

        databaseName = s[0];
        collectionName = s[1];
    }

    private Configuration getConfiguration() throws IOException {
        return Configuration.getInstance();
    }

    @Override
    public void close() {
        commit();
        connection.close();
    }

    private void commit() {
        collection.insert(objs);
        objs = new ArrayList<>();
    }

    @Override
    public void insert(HashMap<String, String> toInsert) {
        if (objs.size() >= 1000)
            commit();

        DBObject e = new BasicDBObject();
        for (String k : toInsert.keySet())
            e.put(k, toInsert.get(k));

        objs.add(e);
    }
}
