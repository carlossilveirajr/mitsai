package mitsai.miner.dataLoader.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import mitsai.miner.apriori.Rule;
import mitsai.miner.dataLoader.commun.WriteFactory;

import java.net.UnknownHostException;

/**
 * Created by junior on 3/15/17.
 */
public class MongoWriter implements WriteFactory {
    private final DBCollection collection;

    public MongoWriter() throws UnknownHostException {
        DB mongo = ConnectionCaller.getInstance().getWriteDatabase();
        collection = mongo.getCollection(ConnectionCaller.getInstance().getWriterCollection());
    }

    @Override
    public void store(Rule rule) {
        DBObject out = new BasicDBObject();
        out.put("cause", rule.getCause().toString());
        out.put("consequence", rule.getConsequence().toString());
        out.put("support", rule.getSupport().toString());
        out.put("confidence", rule.getConfidence().toString());
        out.put("delta_space", rule.getSpace().toString());
        out.put("delta_time", rule.getTime().toString());

        collection.insert(out);
    }
}
