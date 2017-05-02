package mitsai.etl.load.database.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import mitsai.etl.commun.Configuration;
import mitsai.etl.load.database.Target;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by junior on 1/3/17.
 */
abstract class ConnectionHandler implements Target {
    private final Cluster cluster;
    private static Session session;

    String keyspace;
    private String address;
    private int port;


    ConnectionHandler() throws IOException {
        retrieveConfigurationFromProperty();
        cluster = Cluster.builder().addContactPoint(address).withPort(port).build();
        session = cluster.connect(keyspace);
    }

    private void retrieveConfigurationFromProperty() throws IOException {
        String [] s = Configuration.getInstance().getConfiguration("target_address").split(":");
        if (s.length != 2)
            throw new Configuration
                    .BadConfigurationFormatException("target_address: address:port");

        address = s[0];
        port = Integer.valueOf(s[1]);
        keyspace = Configuration.getInstance().getConfiguration("target_name");
    }

    @Override
    public void close() {
        cluster.close();
    }

    @Override
    public void insert(HashMap<String, String> toInsert) {
        session.execute(insertIntoDatabase(toInsert));
    }

    protected abstract String insertIntoDatabase(HashMap<String, String> toInsert);
}
