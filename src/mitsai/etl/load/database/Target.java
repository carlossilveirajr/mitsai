package mitsai.etl.load.database;

import java.util.HashMap;

/**
 * Created by junior on 9/5/16.
 */
public interface Target {
    void close();

    void insert(HashMap<String, String> toInsert);
}
