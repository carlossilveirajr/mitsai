package mitsai.etl.load.database.mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/**
 * Created by junior on 9/4/16.
 */
class Connection {
    private final String host;
    private final int port;
    private final String dbName;

    private MongoClient mongoClient = null;
    private DB database = null;

    public Connection(String host, int port, String dbName) {
        this.host =  host;
        this.port = port;
        this.dbName = dbName;
    }

    public DB get() throws UnknownHostException {
        if (database == null)
            create();
        return database;
    }

    private void create() throws UnknownHostException {
        mongoClient = new MongoClient(host, port);
        database = mongoClient.getDB(dbName);
    }

    public void close() {
        if (mongoClient != null)
            mongoClient.close();
    }
}
