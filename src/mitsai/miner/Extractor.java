package mitsai.miner;

import mitsai.miner.apriori.Apriori;
import mitsai.miner.apriori.Rule;
import mitsai.miner.dataLoader.DataConverter;
import mitsai.miner.dataLoader.DataLoaderFactory;
import mitsai.miner.spaceTime.SpaceTimeData;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by junior on 2/6/17.
 */
public class Extractor {
    private final DataConverter converter;

    public Extractor(DataConverter converter) {
        this.converter = converter;
    }

    public void run(double support, double confidence, int deltaSpace, int deltaTime) throws UnknownHostException {
        DataLoaderFactory data = new DataLoaderFactory(converter);
        SpaceTimeData database = new SpaceTimeData(data.getDatabase(), deltaSpace, deltaTime);

        Miner miner = new Apriori(database);
        miner.run(support, confidence);
        List<Rule> result = miner.getRules();

        data.storeResults(result);
    }
}
