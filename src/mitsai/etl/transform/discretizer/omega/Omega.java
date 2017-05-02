package mitsai.etl.transform.discretizer.omega;

import mitsai.etl.transform.discretizer.Attribute;
import mitsai.etl.transform.discretizer.AttributeList;
import mitsai.etl.transform.discretizer.Discretizer;

/**
 * Created by junior on 3/11/16.
 */
public class Omega implements Discretizer {
    private static final int MIN_ELEMENTS_FOR_EDGES = 100;
    private Attribute[] attributes = new Attribute[0];
    private double [] edges = new double[0];
    private int edgesSize = 0;

    public Omega(AttributeList list) {
        set(list);
    }

    public Omega() {
    }

    public void set(AttributeList list) {
        attributes = list.getSortedAttributes();
        edges = new double[attributes.length];
        if (attributes.length != 0)
            run();
    }

    private void run() {
        defineEdges();
        eliminateCutPointSmallerThan();
        fuseConsecutiveIntervals();
    }

    private void defineEdges() {
        edges[edgesSize++] = attributes[0].value;
        for (int i = 1; i < attributes.length; ++i)
            if (isAnEdges(i))
                edges[edgesSize++] = attributes[i].value;
    }

    private boolean isAnEdges(int index) {
        return attributes[index - 1].value + 0.009 < attributes[index].value
               // It is presented in the original version of Omega, however, this one
               // is not using categories, thus it was removed.
               // && attributes[index - 1].category != attributes[index].category
                ;
    }

    private void eliminateCutPointSmallerThan() {
        double [] allEdges = edges;

        edges = new double[edgesSize];
        edges[0] = allEdges[0];
        edgesSize = 1;

        int j = 1, count = 0;
        for (int i = 0; i < edges.length; ++i) {
            for (; j < attributes.length; ++j)
                if (attributes[j].value <= allEdges[i])
                    ++count;
                else
                    break;

            if(count > MIN_ELEMENTS_FOR_EDGES) {
                edges[edgesSize++] = allEdges[i];
                count = 0;
            }
        }
    }

    public int getCategory(double value) {
        for (int i = 0; i < edgesSize; ++i)
            if (edges[i] > value)
                return i;
        return edgesSize;
    }

    public String getInterval(double value) {
        Double before = -Double.MAX_VALUE, after = 0.0;
        for (int i = 0; i < edgesSize; ++i) {
            if (edges[i] >= value) {
                after = edges[i];
                break;
            }
            else
                before = edges[i];
        }

        return String.format("(%.3f;%.3f]", before, after);
    }

    private void fuseConsecutiveIntervals() {
        // To be implemented if necessary
    }
}
