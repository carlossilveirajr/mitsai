package mitsai.miner;

import javafx.util.Pair;

import java.time.LocalDate;

/**
 * Created by ecaosir on 2/10/2016.
 */
public interface Item {
    LocalDate getTime();
    Pair<Integer, Integer> getLocation();
    Integer getId();
    String getValue();
}
