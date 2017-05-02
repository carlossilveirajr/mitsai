package mitsai.miner.dataLoader;

import mitsai.miner.Item;

import java.util.List;

/**
 * Created by junior on 10/27/15.
 */
public interface DataConverter<DatabaseEntity> {
    List<Item> convertInput(DatabaseEntity record);
}
