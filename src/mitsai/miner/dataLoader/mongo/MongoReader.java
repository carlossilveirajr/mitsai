package mitsai.miner.dataLoader.mongo;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import mitsai.miner.Item;
import mitsai.miner.dataLoader.DataConverter;
import mitsai.miner.dataLoader.commun.DatabaseInMemory;
import mitsai.miner.dataLoader.commun.LoadFactory;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by junior on 10/26/15.
 */
public class MongoReader extends LoadFactory<DBObject> {
    private final DatabaseInMemory database;

    public MongoReader(DataConverter<DBObject> converter) {
        super(converter);
        database = new DatabaseInMemory();
    }

    @Override
    public DatabaseInMemory fieldDatabase() throws UnknownHostException {
        DB mongo = ConnectionCaller.getInstance().getReaderDatabase();
        setQuery(ConnectionCaller.getInstance().getReaderCollection());

        extractDatabaseElements(mongo.getCollection(query).find());
        ConnectionCaller.close();

        return database;
    }

    private void extractDatabaseElements(DBCursor elements) {
        while(elements.hasNext()) {
            DBObject element = elements.next();
            try {
                List<Item> converted = converter.convertInput(element);
                for (Item i : converted) {
                    if (database.getItems().contains(i))
                        break;

                    database.set(i);
                }
            } catch (Exception ignored) { }
        }
    }
}
