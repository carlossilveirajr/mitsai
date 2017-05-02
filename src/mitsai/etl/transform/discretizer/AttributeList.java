package mitsai.etl.transform.discretizer;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by junior on 3/10/16.
 */
public class AttributeList {
    private final Set<Attribute> attributes = new HashSet<>();

    public void add(double attribute, int category) throws NegativeCategory {
        if (category < 0)
            throw new NegativeCategory();

        attributes.add(new Attribute(attribute, category));
    }

    public int size() {
        return attributes.size();
    }

    public Attribute[] getSortedAttributes() {
        return (attributes.stream()
                .sorted(Comparator.comparingDouble(e -> e.value)))
                .toArray(Attribute[]::new);
    }

    public class NegativeCategory extends RuntimeException { }
}
