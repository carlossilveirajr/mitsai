package mitsai.etl.load.database.cassandra;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by junior on 1/3/17.
 */
public class Cassandra extends ConnectionHandler {
    private boolean firstTime = true;

    public Cassandra() throws IOException { }

    @Override
    protected String insertIntoDatabase(HashMap<String, String> toInsert) {
        return !firstTime ? createLineToInsert(toInsert) :
                createTableInTheFirstTime(toInsert) + createLineToInsert(toInsert);
    }

    private String createTableInTheFirstTime(HashMap<String, String> toInsert) {
        firstTime = false;

        String fields = toInsert.keySet()
                .stream().collect(Collectors.joining(" VARCHAR,"));
        return "CREATE TABLE IF NOT EXISTS " + keyspace + "(" + fields + " VARCHAR);";
    }

    private String createLineToInsert(HashMap<String, String> toInsert) {
        String values = toInsert.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.joining("', '"));
        return "INSERT INTO " + keyspace + " VALUES ('" + values + "');";
    }
}
