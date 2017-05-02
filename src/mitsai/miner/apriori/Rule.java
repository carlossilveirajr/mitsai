package mitsai.miner.apriori;

/**
 * Created by junior on 1/20/15.
 */
public class Rule {
    private final Itemset cause;
    private final Itemset consequence;
    private double support;
    private double confidence;
    private double spaceDistance;
    private double temporalDistance;

    public Rule() {
        this(new Itemset(), new Itemset());
    }

    public Rule(Itemset cause, Itemset consequence) {
        this(cause, consequence, 0.0, 0.0);
    }

    public Rule(Itemset cause, Itemset consequence, double support, double confidence) {
        this.cause = cause;
        this.consequence = consequence;
        this.support = support;
        this.confidence = confidence;
        this.spaceDistance = 0.0;
        this.temporalDistance = 0.0;
    }

    public Rule(Itemset cause, Itemset consequence, double support, double confidence,
                double time, double space) {
        this(cause, consequence, support, confidence);
        spaceDistance = space;
        temporalDistance = time;
    }

    public Itemset getCause() {
        return cause;
    }

    public Itemset getConsequence() {
        return consequence;
    }

    public Double getSupport() {
        return support;
    }

    public Double getConfidence() {
        return confidence;
    }

    private boolean isValidGreatThanZero(String name, Double value) throws InvalidValue {
        if (value != null)
            if (value <= 0.0 || value > 1)
                throw new InvalidValue(name, value);
            else
                return true;
        else
            return false;
    }

    public void adjust(Double support, Double confidence) throws InvalidValue {
        if (isValidGreatThanZero("Support", support))
            this.support = support;

        if (isValidGreatThanZero("Confidence", confidence))
            this.confidence = confidence;
    }

    @Override
    public String toString() {
        return String.format("%s => %s (sup=%.3f,conf=%.3f,time=%.3f,space=%.3f)",
                cause, consequence, support, confidence, temporalDistance, spaceDistance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        return cause.equals(rule.cause) && consequence.equals(rule.consequence);
    }

    @Override
    public int hashCode() {
        int result = cause.hashCode();
        result = 31 * result + consequence.hashCode();
        return result;
    }

    public Double getSpace() {
        return spaceDistance;
    }

    public Double getTime() {
        return temporalDistance;
    }
}

