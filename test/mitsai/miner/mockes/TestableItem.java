package mitsai.miner.mockes;

import javafx.util.Pair;
import mitsai.miner.Item;

import java.time.LocalDate;

/**
 * Created by junior on 2/5/17.
 */
public class TestableItem implements Item {
    private final String item;

    public TestableItem(String item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestableItem that = (TestableItem) o;

        return item.equals(that.item);
    }

    @Override
    public int hashCode() {
        return item.hashCode();
    }

    @Override
    public String toString() {
        return item;
    }

    @Override
    public LocalDate getTime() {
        return LocalDate.now();
    }

    @Override
    public Pair<Integer, Integer> getLocation() {
        return null;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public String getValue() {
        return item;
    }

}

