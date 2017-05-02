package mitsai.miner.dataLoader.commun;

import mitsai.miner.apriori.Rule;

/**
 * Created by junior on 3/15/17.
 */
public interface WriteFactory {
    void store(Rule rule);
}
