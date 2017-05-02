package mitsai.etl.transform.discretizer;

/**
 * Created by junior on 3/11/16.
 */
public class Attribute {
    public final double value;
    private final int category;

    public Attribute(double attribute, int category) {
        this.value = attribute;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute)) return false;

        Attribute attribute1 = (Attribute) o;
        return category == attribute1.category &&
                Double.compare(attribute1.value, value) == 0;
    }

    @Override
    public int hashCode() {
        long attribute = Double.doubleToLongBits(this.value);
        return 31 * ((int) (attribute ^ (attribute >>> 32))) + category;
    }
}
