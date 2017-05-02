package mitsai.miner;

import mitsai.miner.apriori.Rule;

import java.util.List;

/**
 * Created by junior on 1/18/15.
 */
public interface Miner {
    void run(double minimumSupport, double minimumConfidence);
    List<Rule> getRules();

    class NullDatabaseLoader extends RuntimeException {}
    class InvalidInputCondition extends RuntimeException {
        public InvalidInputCondition(String valueName) {
            super("Invalid Value: " + valueName);
        }
    }
}
