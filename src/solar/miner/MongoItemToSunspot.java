package solar.miner;

import com.mongodb.DBObject;
import mitsai.miner.Item;
import mitsai.miner.dataLoader.DataConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junior on 2/6/17.
 */
public class MongoItemToSunspot implements DataConverter<DBObject> {
    private String date;
    private String location;
    private Integer number;

    @Override
    public List<Item> convertInput(DBObject item) {
        List<Item> out = new ArrayList<>();

        date = (String) item.get("date");
        location = (String) item.get("location");
        number = Integer.valueOf((String) item.get("number"));

        out.add(extractTextualSunspotFromDatabaseObject(item));

        String area = (String) item.get("area");
        String feature = (String) item.get("featureDiscrete");
        out.add(new FeatureSunspot(date, location, number, feature, area));

        return out;
    }

    private TextualSunspot extractTextualSunspotFromDatabaseObject(DBObject registry) {
        final String value = ((String) registry.get("mcintoshClass")).split("/")[0];
        return new TextualSunspot(date, location, number, value);
    }
}
