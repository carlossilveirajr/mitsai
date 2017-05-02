package mitsai.etl.extract.reader;

import mitsai.etl.commun.Item;

import java.util.List;

/**
 * Created by junior on 9/1/16.
 */
public interface Reader {
    void invokeFor(String filePath) throws Exception;
    List<Item> getItems();
}
