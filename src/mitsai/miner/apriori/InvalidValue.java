package mitsai.miner.apriori;

/**
 * Created by junior on 9/10/15.
 */
class InvalidValue extends RuntimeException {
    public InvalidValue(String field, Double value) {
        super("Invalid value of " + field + " = " + value);
    }
}
